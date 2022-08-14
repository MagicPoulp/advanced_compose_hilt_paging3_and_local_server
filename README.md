# Author

Thierry Vilmart
August 2022

# Test setup

In Constants.kt, there are different booleans to use a fake Virus hash, to see all scenarios.
The production set is active using a real VirusTotalUrl and all packages.
You can try to use the trial example to see what happens when all packages are analysed, with the UI that updates
like on the screenshot in the doc folder.

# Architecture

Repositories -> ViewModel qui change les LiveData  -> UI avec observeAsState() sur les LiveData.
Voir la doc :
https://developer.android.com/jetpack/compose/architecture

Les LiveData come State apportent une optimization car on ne change que la partie de l'UI associée quand la valeur change.
De plus on a pas à calculer les changements sur un gros état plein de valeurs.
De plus les observables peuvent être combinés pour faire tout comportement imaginable (debounce, etc).

La file d'attente, pour respecter le quota de 4 requêtes par minute, est faite ainsi :
Dans
fun analyseFileHashes(hashes: List<String>): Flow<Pair<String, Any>> = hashes
avec un
.flatMapMerge(concurrency = maxConcurrentConnectionsOnVirusTotal)

# Detailed information

On Android 11, an additional field is required in the Manifest to get the list of installed applications.
https://proandroiddev.com/how-to-get-users-installed-apps-in-android-11-b4a4d2754286
https://stackoverflow.com/questions/60679685/what-does-query-all-packages-permission-do

Blog for using Flow with LiveData
https://betterprogramming.pub/no-more-livedata-in-repositories-in-kotlin-85f5a234a8fe

Retrofit with coroutines
Note that Retrofit can limit the number of requests
https://www.geeksforgeeks.org/retrofit-with-kotlin-coroutine-in-android/

# Not done due to lack of time

The UI could look much better with better colors, fonts, and a proper design. But it takes time.

We need many unit tests where the logic is complex.

We only hash the MD5, but perhaps we should also test the SHA1 and the SHA256,
since VirusTotal supports the 3 hashes types
https://support.virustotal.com/hc/en-us/articles/115002739245-Searching

mapHashToVirusStatusHistory could be put in persistent memory to speed up the analysis with known hashes.

