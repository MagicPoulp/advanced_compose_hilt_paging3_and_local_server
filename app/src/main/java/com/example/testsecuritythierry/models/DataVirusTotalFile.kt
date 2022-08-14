package com.example.testsecuritythierry.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/*
curl -v "https://www.virustotal.com/api/v3/files/142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e" --header "x-apikey: 56c010236980d58a0a608f8b447517143b3d89878cd4ccee421e9cdc540550cc"
*   Trying 74.125.34.46...
* TCP_NODELAY set
* Connected to www.virustotal.com (74.125.34.46) port 443 (#0)
* ALPN, offering h2
* ALPN, offering http/1.1
* successfully set certificate verify locations:
*   CAfile: /etc/ssl/cert.pem
  CApath: none
* TLSv1.2 (OUT), TLS handshake, Client hello (1):
* TLSv1.2 (IN), TLS handshake, Server hello (2):
* TLSv1.2 (IN), TLS handshake, Certificate (11):
* TLSv1.2 (IN), TLS handshake, Server key exchange (12):
* TLSv1.2 (IN), TLS handshake, Server finished (14):
* TLSv1.2 (OUT), TLS handshake, Client key exchange (16):
* TLSv1.2 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.2 (OUT), TLS handshake, Finished (20):
* TLSv1.2 (IN), TLS change cipher, Change cipher spec (1):
* TLSv1.2 (IN), TLS handshake, Finished (20):
* SSL connection using TLSv1.2 / ECDHE-RSA-CHACHA20-POLY1305
* ALPN, server accepted to use h2
* Server certificate:
*  subject: C=ES; L=Malaga; O=VirusTotal SL; CN=*.virustotal.com
*  start date: Jan 17 00:00:00 2022 GMT
*  expire date: Jan 18 23:59:59 2023 GMT
*  subjectAltName: host "www.virustotal.com" matched cert's "*.virustotal.com"
*  issuer: C=US; O=DigiCert Inc; CN=DigiCert TLS RSA SHA256 2020 CA1
*  SSL certificate verify ok.
* Using HTTP2, server supports multi-use
* Connection state changed (HTTP/2 confirmed)
* Copying HTTP/2 data in stream buffer to connection buffer after upgrade: len=0
* Using Stream ID: 1 (easy handle 0x7f971880f800)
> GET /api/v3/files/142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e HTTP/2
> Host: www.virustotal.com
> User-Agent: curl/7.64.1
> Accept: *
> x-apikey: 56c010236980d58a0a608f8b447517143b3d89878cd4ccee421e9cdc540550cc
>
* Connection state changed (MAX_CONCURRENT_STREAMS == 100)!
< HTTP/2 200
< cache-control: no-cache
< content-type: application/json; charset=utf-8
< x-cloud-trace-context: 9a82c15ce6f44c16c4175d5289716b49
< date: Sun, 14 Aug 2022 09:34:06 GMT
< server: Google Frontend
< content-length: 33879
<
{
"data": {
  "attributes": {
    "type_description": "Android",
    "tlsh": "T12965D00299B36D96C5F8E2F0C3E690E9A3343EB65612C1473EB7B7E9017F3901664798",
    "vhash": "218c5f962271a04167ad4f15e3429cd0",
    "trid": [
    {
        "file_type": "Android Package",
        "probability": 67.5
    },
    {
        "file_type": "Java Archive",
        "probability": 23.6
    },
    {
        "file_type": "ZIP compressed archive",
        "probability": 7.0
    },
    {
        "file_type": "PrintFox/Pagefox bitmap (640x800)",
        "probability": 1.7
    }
    ],
    "names": [
    "test",
    "403898",
    "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e.zip",
    "12602de6659a356141e744bf569e7e56.apk",
    "vti-rescan",
    "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
    "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e.bin"
    ],
    "last_modification_date": 1658869685,
    "type_tag": "android",
    "times_submitted": 22,
    "total_votes": {
    "harmless": 8,
    "malicious": 3
},
    "size": 1415759,
    "popular_threat_classification": {
    "suggested_threat_label": "trojan.fakeinst/boxersms",
    "popular_threat_category": [
    {
        "count": 21,
        "value": "trojan"
    }
    ],
    "popular_threat_name": [
    {
        "count": 10,
        "value": "fakeinst"
    },
    {
        "count": 3,
        "value": "boxersms"
    },
    {
        "count": 3,
        "value": "boxer"
    }
    ]
},
    "last_submission_date": 1602486053,
    "last_analysis_results": {
    "Bkav": {
    "category": "undetected",
    "engine_name": "Bkav",
    "engine_version": "1.3.0.9899",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Lionic": {
    "category": "malicious",
    "engine_name": "Lionic",
    "engine_version": "7.5",
    "result": "SUSPICIOUS",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "tehtris": {
    "category": "type-unsupported",
    "engine_name": "tehtris",
    "engine_version": null,
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Cynet": {
    "category": "malicious",
    "engine_name": "Cynet",
    "engine_version": "4.0.0.27",
    "result": "Malicious (score: 99)",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "CMC": {
    "category": "undetected",
    "engine_name": "CMC",
    "engine_version": "2.10.2019.1",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220623"
},
    "CAT-QuickHeal": {
    "category": "malicious",
    "engine_name": "CAT-QuickHeal",
    "engine_version": "14.00",
    "result": "Android.FakeInst.D",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "McAfee": {
    "category": "malicious",
    "engine_name": "McAfee",
    "engine_version": "6.0.6.653",
    "result": "Artemis!12602DE6659A",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Malwarebytes": {
    "category": "undetected",
    "engine_name": "Malwarebytes",
    "engine_version": "4.3.3.37",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Zillya": {
    "category": "undetected",
    "engine_name": "Zillya",
    "engine_version": "2.0.0.4678",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Paloalto": {
    "category": "type-unsupported",
    "engine_name": "Paloalto",
    "engine_version": "0.9.0.1003",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Sangfor": {
    "category": "malicious",
    "engine_name": "Sangfor",
    "engine_version": "2.14.0.0",
    "result": "Malware.Android-Script.Save.460d4abe",
    "method": "blacklist",
    "engine_update": "20220722"
},
    "K7AntiVirus": {
    "category": "undetected",
    "engine_name": "K7AntiVirus",
    "engine_version": "12.27.43524",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Alibaba": {
    "category": "malicious",
    "engine_name": "Alibaba",
    "engine_version": "0.3.0.5",
    "result": "Trojan:Android/BoxerSms.8bcb925b",
    "method": "blacklist",
    "engine_update": "20190527"
},
    "K7GW": {
    "category": "malicious",
    "engine_name": "K7GW",
    "engine_version": "12.27.43526",
    "result": "Trojan ( 000001021 )",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Trustlook": {
    "category": "malicious",
    "engine_name": "Trustlook",
    "engine_version": "1.0",
    "result": "Android.Malware.Trojan",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "BitDefenderTheta": {
    "category": "undetected",
    "engine_name": "BitDefenderTheta",
    "engine_version": "7.2.37796.0",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220718"
},
    "VirIT": {
    "category": "malicious",
    "engine_name": "VirIT",
    "engine_version": "9.5.246",
    "result": "Android.Adw.X-Gen.IGJ",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Cyren": {
    "category": "malicious",
    "engine_name": "Cyren",
    "engine_version": "6.5.1.2",
    "result": "AndroidOS/FakeInst.D",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "SymantecMobileInsight": {
    "category": "malicious",
    "engine_name": "SymantecMobileInsight",
    "engine_version": "2.0",
    "result": "Trojan:Opfake",
    "method": "blacklist",
    "engine_update": "20220208"
},
    "Symantec": {
    "category": "malicious",
    "engine_name": "Symantec",
    "engine_version": "1.18.0.0",
    "result": "Trojan.Gen.2",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Elastic": {
    "category": "type-unsupported",
    "engine_name": "Elastic",
    "engine_version": "4.0.40",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220623"
},
    "ESET-NOD32": {
    "category": "malicious",
    "engine_name": "ESET-NOD32",
    "engine_version": "25655",
    "result": "multiple detections",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Baidu": {
    "category": "undetected",
    "engine_name": "Baidu",
    "engine_version": "1.0.0.2",
    "result": null,
    "method": "blacklist",
    "engine_update": "20190318"
},
    "TrendMicro-HouseCall": {
    "category": "malicious",
    "engine_name": "TrendMicro-HouseCall",
    "engine_version": "10.0.0.1040",
    "result": "AndroidOS_TROJSMS.BLK",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Avast": {
    "category": "malicious",
    "engine_name": "Avast",
    "engine_version": "21.1.5827.0",
    "result": "Android:Agent-HGV [Trj]",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "ClamAV": {
    "category": "undetected",
    "engine_name": "ClamAV",
    "engine_version": "0.105.1.0",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Kaspersky": {
    "category": "malicious",
    "engine_name": "Kaspersky",
    "engine_version": "21.0.1.45",
    "result": "HEUR:Trojan-SMS.AndroidOS.FakeInst.a",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "BitDefender": {
    "category": "undetected",
    "engine_name": "BitDefender",
    "engine_version": "7.2",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "NANO-Antivirus": {
    "category": "malicious",
    "engine_name": "NANO-Antivirus",
    "engine_version": "1.0.146.25618",
    "result": "Trojan.Android.FakeInst.dtphzp",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "ViRobot": {
    "category": "undetected",
    "engine_name": "ViRobot",
    "engine_version": "2014.3.20.0",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "MicroWorld-eScan": {
    "category": "undetected",
    "engine_name": "MicroWorld-eScan",
    "engine_version": "14.0.409.0",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Tencent": {
    "category": "malicious",
    "engine_name": "Tencent",
    "engine_version": "1.0.0.1",
    "result": "Trojan.Android.FakeInst.aaaa",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Ad-Aware": {
    "category": "undetected",
    "engine_name": "Ad-Aware",
    "engine_version": "3.0.21.193",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Emsisoft": {
    "category": "undetected",
    "engine_name": "Emsisoft",
    "engine_version": "2021.5.0.7597",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Comodo": {
    "category": "malicious",
    "engine_name": "Comodo",
    "engine_version": "34840",
    "result": "Malware@#aiy9dvdicqd2",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "F-Secure": {
    "category": "malicious",
    "engine_name": "F-Secure",
    "engine_version": "18.10.978.51",
    "result": "Trojan:Android/Boxer.C",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "DrWeb": {
    "category": "malicious",
    "engine_name": "DrWeb",
    "engine_version": "7.0.56.4040",
    "result": "Android.SmsSend.222.origin",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "VIPRE": {
    "category": "undetected",
    "engine_name": "VIPRE",
    "engine_version": "6.0.0.35",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "TrendMicro": {
    "category": "malicious",
    "engine_name": "TrendMicro",
    "engine_version": "11.0.0.1006",
    "result": "AndroidOS_TROJSMS.BLK",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "McAfee-GW-Edition": {
    "category": "malicious",
    "engine_name": "McAfee-GW-Edition",
    "engine_version": "v2019.1.2+3728",
    "result": "Artemis!Trojan",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "SentinelOne": {
    "category": "type-unsupported",
    "engine_name": "SentinelOne",
    "engine_version": "22.2.1.2",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220330"
},
    "Trapmine": {
    "category": "type-unsupported",
    "engine_name": "Trapmine",
    "engine_version": "3.5.48.101",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220707"
},
    "FireEye": {
    "category": "undetected",
    "engine_name": "FireEye",
    "engine_version": "35.24.1.0",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Sophos": {
    "category": "malicious",
    "engine_name": "Sophos",
    "engine_version": "1.4.1.0",
    "result": "Andr/Boxer-D",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "APEX": {
    "category": "type-unsupported",
    "engine_name": "APEX",
    "engine_version": "6.316",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220725"
},
    "GData": {
    "category": "undetected",
    "engine_name": "GData",
    "engine_version": "A:25.33600B:27.28218",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Jiangmin": {
    "category": "malicious",
    "engine_name": "Jiangmin",
    "engine_version": "16.0.100",
    "result": "Trojan/AndroidOS.vn",
    "method": "blacklist",
    "engine_update": "20220725"
},
    "Webroot": {
    "category": "type-unsupported",
    "engine_name": "Webroot",
    "engine_version": "1.0.0.403",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Avira": {
    "category": "malicious",
    "engine_name": "Avira",
    "engine_version": "8.3.3.16",
    "result": "ANDROID/FakeInstall.AU.Gen",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Antiy-AVL": {
    "category": "malicious",
    "engine_name": "Antiy-AVL",
    "engine_version": "3.0",
    "result": "Trojan/Generic.ASMalwAD.30",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Kingsoft": {
    "category": "malicious",
    "engine_name": "Kingsoft",
    "engine_version": "2017.9.26.565",
    "result": "Android.Troj.FakeInst.ic.(kcloud)",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Microsoft": {
    "category": "malicious",
    "engine_name": "Microsoft",
    "engine_version": "1.1.19400.3",
    "result": "Trojan:AndroidOS/BoxerSms.C",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Gridinsoft": {
    "category": "undetected",
    "engine_name": "Gridinsoft",
    "engine_version": "1.0.86.174",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Arcabit": {
    "category": "undetected",
    "engine_name": "Arcabit",
    "engine_version": "1.0.0.889",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "SUPERAntiSpyware": {
    "category": "undetected",
    "engine_name": "SUPERAntiSpyware",
    "engine_version": "5.6.0.1032",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220723"
},
    "ZoneAlarm": {
    "category": "malicious",
    "engine_name": "ZoneAlarm",
    "engine_version": "1.0",
    "result": "HEUR:Trojan-SMS.AndroidOS.FakeInst.a",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Avast-Mobile": {
    "category": "malicious",
    "engine_name": "Avast-Mobile",
    "engine_version": "220726-00",
    "result": "Android:Agent-HGV [Trj]",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "TACHYON": {
    "category": "undetected",
    "engine_name": "TACHYON",
    "engine_version": "2022-07-26.02",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "BitDefenderFalx": {
    "category": "malicious",
    "engine_name": "BitDefenderFalx",
    "engine_version": "2.0.936",
    "result": "Android.Trojan.FakeInst.M",
    "method": "blacklist",
    "engine_update": "20220103"
},
    "AhnLab-V3": {
    "category": "malicious",
    "engine_name": "AhnLab-V3",
    "engine_version": "3.22.1.10283",
    "result": "Trojan/Android.FakeInst.28283",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Acronis": {
    "category": "undetected",
    "engine_name": "Acronis",
    "engine_version": "1.2.0.108",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220426"
},
    "ALYac": {
    "category": "undetected",
    "engine_name": "ALYac",
    "engine_version": "1.1.3.1",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "MAX": {
    "category": "undetected",
    "engine_name": "MAX",
    "engine_version": "2019.9.16.1",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "VBA32": {
    "category": "malicious",
    "engine_name": "VBA32",
    "engine_version": "5.0.0",
    "result": "Trojan-SMS.AndroidOS.FakeInst.a",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Cylance": {
    "category": "type-unsupported",
    "engine_name": "Cylance",
    "engine_version": "2.3.1.101",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Zoner": {
    "category": "undetected",
    "engine_name": "Zoner",
    "engine_version": "2.2.2.0",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220725"
},
    "Rising": {
    "category": "malicious",
    "engine_name": "Rising",
    "engine_version": "25.0.0.27",
    "result": "Trojan.BoxerSms/Android!1.A250 (CLASSIC)",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Yandex": {
    "category": "malicious",
    "engine_name": "Yandex",
    "engine_version": "5.5.2.24",
    "result": "Trojan.Etecer.bSmjBJ.2",
    "method": "blacklist",
    "engine_update": "20220725"
},
    "Ikarus": {
    "category": "malicious",
    "engine_name": "Ikarus",
    "engine_version": "6.0.26.0",
    "result": "Trojan-SMS.AndroidOS.Boxer",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "MaxSecure": {
    "category": "undetected",
    "engine_name": "MaxSecure",
    "engine_version": "1.0.0.1",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Fortinet": {
    "category": "malicious",
    "engine_name": "Fortinet",
    "engine_version": "6.4.258.0",
    "result": "Android/SMSBoxer.AQ!tr",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "AVG": {
    "category": "malicious",
    "engine_name": "AVG",
    "engine_version": "21.1.5827.0",
    "result": "Android:Agent-HGV [Trj]",
    "method": "blacklist",
    "engine_update": "20220726"
},
    "Cybereason": {
    "category": "type-unsupported",
    "engine_name": "Cybereason",
    "engine_version": "1.2.449",
    "result": null,
    "method": "blacklist",
    "engine_update": "20210330"
},
    "Panda": {
    "category": "undetected",
    "engine_name": "Panda",
    "engine_version": "4.6.4.2",
    "result": null,
    "method": "blacklist",
    "engine_update": "20220726"
},
    "CrowdStrike": {
    "category": "type-unsupported",
    "engine_name": "CrowdStrike",
    "engine_version": "1.0",
    "result": null,
    "method": "blacklist",
    "engine_update": null
}
},
    "sandbox_verdicts": {
    "Zenbox Linux": {
    "category": "harmless",
    "confidence": 2,
    "sandbox_name": "Zenbox Linux",
    "malware_classification": [
    "CLEAN"
    ]
}
},
    "sha256": "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
    "type_extension": "apk",
    "tags": [
    "apk",
    "android"
    ],
    "last_analysis_date": 1658862466,
    "unique_sources": 21,
    "first_submission_date": 1345538523,
    "sha1": "43367a157685ffbbb557f7cc016dc0b921b7a370",
    "ssdeep": "24576:GUz8BK3QV+7k50LCZoPWtMDi9mf4pSbE1evGdY/yJk7+VIPotWL0ZCHgNgH2js4i:GUz8BK3QV+7k50LCZoPWtMDi9mf4pSbl",
    "bundle_info": {
    "highest_datetime": "2012-02-18 12:09:30",
    "lowest_datetime": "2012-02-18 12:09:30",
    "num_children": 85,
    "extensions": {
    "xml": 6,
    "dex": 1,
    "MF": 1,
    "cfg": 1,
    "RSA": 1,
    "SF": 1,
    "png": 3
},
    "file_types": {
    "XML": 6,
    "unknown": 5,
    "DEX": 1,
    "directory": 8,
    "PNG": 65
},
    "type": "APK",
    "uncompressed_size": 1410430
},
    "packers": {
    "F-PROT": "UTF-8"
},
    "md5": "12602de6659a356141e744bf569e7e56",
    "androguard": {
    "Activities": [
    "com.soft.common.Main",
    "com.soft.common.Offert",
    "com.soft.common.GrantAccess"
    ],
    "AndroidVersionCode": "1",
    "main_activity": "com.soft.common.Main",
    "certificate": {
    "Subject": {
    "DN": "C:SD, CN:Code Coder, L:New York, O:Reti, ST:WW, OU:UI",
    "C": "SD",
    "CN": "Code Coder",
    "L": "New York",
    "O": "Reti",
    "ST": "WW",
    "OU": "UI"
},
    "validto": "2038-12-02 19:29:30",
    "serialnumber": "4e23381a",
    "thumbprint": "5cb0136d4f218b1d7a489024972f8aa4720f5e67",
    "validfrom": "2011-07-17 19:29:30",
    "Issuer": {
    "DN": "C:SD, CN:Code Coder, L:New York, O:Reti, ST:WW, OU:UI",
    "C": "SD",
    "CN": "Code Coder",
    "L": "New York",
    "O": "Reti",
    "ST": "WW",
    "OU": "UI"
}
},
    "VTAndroidInfo": 2.0,
    "Package": "com.soft.common",
    "intent_filters": {
    "Activities": {
    "com.soft.common.Main": {
    "action": [
    "android.intent.action.MAIN"
    ],
    "category": [
    "android.intent.category.LAUNCHER"
    ]
}
}
},
    "permission_details": {
    "android.permission.SEND_SMS": {
    "short_description": "send SMS messages",
    "full_description": "Allows application to send SMS\n      messages. Malicious applications may cost you money by sending\n      messages without your confirmation.",
    "permission_type": "dangerous"
},
    "android.permission.WRITE_EXTERNAL_STORAGE": {
    "short_description": "modify/delete SD card contents",
    "full_description": "Allows an application to write to the SD card.",
    "permission_type": "dangerous"
},
    "android.permission.READ_PHONE_STATE": {
    "short_description": "read phone state and identity",
    "full_description": "Allows the application to access the phone\n        features of the device.  An application with this permission can determine the phone\n        number and serial number of this phone, whether a call is active, the number that call\n        is connected to and the like.",
    "permission_type": "dangerous"
},
    "android.permission.INTERNET": {
    "short_description": "full Internet access",
    "full_description": "Allows an application to\n      create network sockets.",
    "permission_type": "dangerous"
}
},
    "AndroidApplication": 1,
    "AndroidApplicationError": false,
    "AndroidVersionName": "1.0",
    "RiskIndicator": {
    "APK": {
    "DEX": 1
},
    "PERM": {
    "DANGEROUS": 1,
    "PRIVACY": 1,
    "MONEY": 1,
    "SMS": 1,
    "INTERNET": 1
}
},
    "MinSdkVersion": "5",
    "TargetSdkVersion": "5",
    "AndroguardVersion": "3.4.0a1",
    "AndroidApplicationInfo": "APK"
},
    "magic": "Zip archive data, at least v2.0 to extract",
    "last_analysis_stats": {
    "harmless": 0,
    "type-unsupported": 10,
    "suspicious": 0,
    "confirmed-timeout": 0,
    "timeout": 0,
    "failure": 0,
    "malicious": 39,
    "undetected": 26
},
    "meaningful_name": "test",
    "reputation": 5
},
    "type": "file",
    "id": "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
    "links": {
    "self": "https://www.virustotal.com/api/v3/files/142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e"
}
}
 * Connection #0 to host www.virustotal.com left intact
}* Closing connection 0
*/
@JsonIgnoreProperties(ignoreUnknown = true)
data class DataVirusTotalFile(
    @JsonProperty("data")
    val data: DataVirusTotalFileData,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataVirusTotalFileData(
    @JsonProperty("attributes")
    val attributes: DataVirusTotalFileAttributes,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("id")
    val id: String,
    @JsonProperty("links")
    val links: DataVirusTotalFileLinks,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataVirusTotalFileAttributes(
    val type_description: String,
    val tlsh: String,
    val vhash: String,
    val md5: String,
    val sha256: String,
    val sha1: String,
    val meaningful_name: String,
    val reputation: Int,
    val last_analysis_stats: DataVirusTotalFileLastAnalysisStats,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataVirusTotalFileLastAnalysisStats(
    val harmless: Int,
    @JsonProperty("type-unsupported")
    val typeUnsupported: Int,
    val suspicious: Int,
    @JsonProperty("confirmed-timeout")
    val confirmedTimeout: Int,
    val timeout: Int,
    val failure: Int,
    val malicious: Int,
    val undetected: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataVirusTotalFileLinks(
    @JsonProperty("self")
    val self: String
)