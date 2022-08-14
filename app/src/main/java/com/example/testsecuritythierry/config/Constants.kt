package com.example.testsecuritythierry.config

const val analysisRefreshInterval = 30*1000L
const val maxConcurrentConnectionsOnVirusTotal = 4
// it must end with a slash
const val virusTotalBaseUrl = "https://www.virustotal.com/"
// to test the network without consuming the quotas
const val virusTotalBaseUrlFake = "https://www.google.com/"
const val virus1 = "virus12345"
const val hashOfVirus1 = "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e"

// Try those booleans to make small experiments with few packages
/*
const val fivePackagesOnly = true
const val manuallyAddAVirus = true
const val useAFakeUrlForVirusTotal = true
*/

// Production mode:
const val fivePackagesOnly = false
const val manuallyAddAVirus = true
const val useAFakeUrlForVirusTotal = false
