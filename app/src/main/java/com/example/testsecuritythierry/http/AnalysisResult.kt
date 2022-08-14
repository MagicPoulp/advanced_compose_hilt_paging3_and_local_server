package com.example.testsecuritythierry.http

open class AnalysisResult {
}

class AnalysisResultError: AnalysisResult() {
}

class AnalysisResultNoThreat: AnalysisResult() {
}

class AnalysisResultVirusFound: AnalysisResult() {
}
