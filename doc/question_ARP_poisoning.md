# Question sur l'attaque ARP poisoning

Réaliser une documentation permettant de répondre aux deux points suivants :

• Le protocole à suivre pour réaliser une attaque de type ARP Poisoning sur le téléphone

• L'algorithme qui permettrait de détecter depuis votre application une attaque ARP
Poisoning ciblant le téléphone


------------------------------------------------------------------------------------------


## 1. Le protocole à suivre pour réaliser une attaque de type ARP poisoning sur le téléphone

### 1.1 Une démarche concrète avec des commandes linux

Voir 1.2 pour une présentation plus complète. L'IP représente l'adresse de haut niveau (OSI Layer 3, Network), tandis que le MAC représente l'adresse physique du device électronique (couche OSI Layer 2, Data-Link). En résumé, il faut envoyer des requêtes ARP sur le réseau, à destination du device et du router pas tout le réseau, pour mettre à jour le cache ARP du device et du router. On associe alors les IPs du device mobile et du router avec le MAC de l'attaqueur. Le routeur aura le MAC de l'attaqueur associé à l'IP du device mobile, et le device mobile aura le MAC de l'attaqueur associé à l'IP du routeur.

Concrétement, la démarche exacte avec les commandes linux est donnée dans [4]. (avec dsniff, netdiscover, arpspoof, eturlsnarf, ou bien netcat, wireshark)

netdiscover
inspecte le réseau et trouve le router

echo 1 > /proc/sys/net/ipv4/ip_forward
enable port forwarding sur le système

arpspoofing -i eth0 -t <VICTIM_IP> <ROUTER_IP>
arpspoofing -i eth0 -t <ROUTER_IP> <VICTIM_IP>
Au niveau des caches ARP du réseau, cela remplace l'associe les IP de la victime et du router avec le MAC de notre machine.

urlsnarf -i eth0
inspecte les transmissions

Cela était fait pour un Man in the Middle.
Pour un Denial of service, il faudrait associer plein d'IPs avec le MAC de la victime ou du router. Puis des ordinateurs zombies infectés sur le grand internet viendrait spammer les IPs en question. De plus, le device mobile ne pourrait plus mettre à jour l'ARP car le spamming noierait ses requêtes ARP.

### 1.2 Plus d'explications

Il y a plein d'articles sur internet. Cependant la connaissance exacte et de qualité se trouve dans les articles publiés dans les revues scientifiques.
Voir dans le dossier "ARM security articles", l'article [1] qui donne un résumé général. [0] donne d'autres détails. Les autres articles concernent les failles dans l'encryptage avec HTTPS et SSL/TLS, quand les certificats sont self-signed, et quand les systèmes d'exploitations ne sont pas mis à jours (vieux software openSSL).

ARP est expliqué très clairement dans [1], et les vulnérabilités sont données plus en détail dans [0] (voir la citation plus bas).

On se restreint à Android dans l'articles [1]. Cependant, la vulnérabilité ne dépend pas d'Android mais du protocole ARP qui est sans état mémorisé et sans authentication au début de l'échange. Donc les téléphones iOS sont autant vulnérables que les téléphones Android.

Il y a essentiellement deux attaques : Denial of service et Man in the middle.

Le Denial of Service vise consiste à envoyer plein de requêtes ARPs pour spammer et noyer les requêtes ARP légitimes qui ne fonctionneront plus. De plus, le Denial of Service peut servir à spammer le réseau et le device mobile avec plein requêtes HTTP car plein d'adresses IP sont associées au MAC du device. Puis des ordinateurs infectés peuvent spammer de requêtes le device et saturer son réseau. De plus, les requêtes ARP du device mobile sur le réseau sont noyées par le flow de spam.

Le Man in the Middle vise à se placer au milieu d'un échange pour l'écouter, le modifier, voire l'interrompre. L'application Windows Wireshark Capture est utilisée dans [1] pour intercepter une transmission et découvrir un mot de passe transmis via HTTP (sans S, sans encryption).

L'attaque Man in the Middle est totallement protégée par l'encryption HTTS/TLS quand les certificats sont bons. Par ailleurs le nouveau standard WPA3 protège contre la vulnérabilité Hole 196 (voir la citation de [5]). Le Hole 196 diffère du ARP poisoning classique par le fait est réalisée par des utilisateurs déjà connectés sur le réseau, par des insiders (voir [5]). En tant qu'insider authentifié, en encryptant le packet ARP avec le group key (GTK), les restrictions ou les protections du réseau sont contournées.


Citation de [0]. (Intéressant concernant les failles d'ARP, malgré les fautes d'anglais)

"AUTHENTICATION MECHANISM OF ARP

As can be seen from the address resolution process, ARP
protocol is very simple and efficient, but insecurity, mainly
manifested in the following four points:

1) ARP relationship mapping table on host base on dynamic
update cache, but there is a time limit on cache refresh, the
attacker can modify the cache before it refreshed[7].

2) ARP request packets on the LAN is a broadcast
transmission, an attacker can by pretending to be a real host
for ARP reply, to intercept real host communication data,
finish the attack[8].

3) A host in Ethernet can send any fake ARP reply
message.ARP protocol is a stateless protocol, based on an
Ethernet on all hosts are trusted, it does not check the packet is
legitimate, also do not check if they send ARP request
accordingly ,if the destination IP address from reply packet as
same as owns, the host will respond, and according to the
packet information store or update the ARP table, this is the
root cause of ARP attack[9].

4) ARP reply packet lack of authentication mechanism,
because of the protocol is considered at the beginning of the
design that the LAN communication between all hosts are fully
trusted, and consideration of the data transmission speed, so
there is not take any security measures in the data link layer,
and no corresponding certification in ARP reply[10]. "


## 2. L'algorithme permettant de détecter une attaque ARP poisoning sur le téléphone

Pour détecter un attaque de ARP poisoning, il suffit d'inspecter l'ARP cache du device, et d'analyser les changements, et les ajouts de devices sur le réseaux. Si une adresse IP qui existe est associée à un autre MAC (l'adresse physique du device), ou inversement, alors on a une suspission d'attaque. Si cela est fait dans les deux sens, alors on a détecté une attaque Man in the middle. Si il y a des centaines d'IP associées au BSSID; alors on a une attaque de Denial of Service.

Sur android, page 5 de l'article [1], les applications android Net Status et Fing permettent de manuellement inspecter le cache ARP sur le device.
Les apps Wifi Protector and WiFi ARP Guard permettent de détecter une attaque automatiquement, plusieurs fois par seconde, avec activation automatique en WLAN, et notifications des attaques détectées. Un gros problème de ces apps réside dans la consommation de batterie.

Dans un réseau local chez soi, le plus efficace consiste à instaurer des tables statiques de cache ARP. Il existe aussi des devices Hardware à installer qui protègent contre l'ARP poisoning.

# Liens

[0] Yongzhen Li1 and Jing Li2, The Research on ARP Protocol Based Authentication Mechanism, International Conference on Applied Mathematics, Simulation and Modelling (AMSM 2016), School of Yanbian University, Yanji 133002, China

[1] Blagoj Nenovski and Pece Mitrevski, Real-World ARP Attacks and Packet Sniffing, Detection and Prevention on Windows and Android Devices, The 12th International Conference for Informatics and Information Technology (CIIT 2015), Faculty of Information and Communication Technologies, St. Clement of Ohrid” University Bitola, Macedonia

[2] La vulnérabilité Hole 196 dans le protocole WPA2 des réseaux sans fils WLAN
https://community.arubanetworks.com/browse/articles/blogviewer?blogkey=da8e9ee7-d28a-4ac9-80f1-1653ac66dd74

[3] Sur un réseau sans fil LAN, au lieu du MAC, il y a le BSSID
https://www.juniper.net/documentation/en_US/junos-space-apps/network-director4.0/topics/concept/wireless-ssid-bssid-essid.html

[4] Démarche concrète réalisant une attaque
https://blog.nvisium.com/everything-you-need-to-know-about-arp-spoofing

[5] Sur la sécurité nouveau standard WPA3 pour le WLAN
https://www.mdpi.com/2079-9292/7/11/284/htm

from [5].AP Session Hijacking
Technique 1: Physical Connection and ARP Spoofing
"An adversary, assuming having connected a malicious AP physically to a network through wired Ethernet, would not be able to send ARP packets to trick the client into thinking it is the real gateway.
Proof. The adversary will then send an ARP packet to the client spoofing the AP’s MAC address with its own IP. The adversary will encrypt the message with the GTK, so the client can decrypt the message with the same GTK, exploiting the Hole 196 vulnerability. A WPA3 router with client isolation turned on, however, will not allow clients in a network to communicate with each other, or know about each other for that matter. Therefore, performing this attack to hijack a client session from a genuine AP is unfeasible."

