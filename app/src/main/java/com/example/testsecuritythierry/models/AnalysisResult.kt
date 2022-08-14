package com.example.testsecuritythierry.models

open class AnalysisResult {
}

class AnalysisResultPending: AnalysisResult() {
}

class AnalysisResultError: AnalysisResult() {
}

class AnalysisResultNoThreat: AnalysisResult() {
}

class AnalysisResultVirusFound: AnalysisResult() {
}
