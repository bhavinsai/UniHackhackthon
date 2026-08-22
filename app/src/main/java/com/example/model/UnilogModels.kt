package com.example.model

data class RawCatalogItem(
    val id: String,
    val mfgPartNum: String,
    val partDesc: String,
    val e1Brand: String? = null,
    val unilogBrand: String? = null,
    val dibBrand: String? = null,
    val partManuf: String? = null,
    val sku: String? = null,
    val dept: String? = null,
    val classCategory: String? = null,
    val fineCategory: String? = null
)

data class EnrichedAttribute(
    val name: String,
    val rawValue: String,
    val normalizedValue: String,
    val unit: String? = null,
    val isLovMatched: Boolean = true,
    val isFilterable: Boolean = true
)

enum class ComplianceStatus {
    PASSED,
    WARNING,
    FAILED
}

data class ValidationRuleResult(
    val ruleName: String,
    val status: ComplianceStatus,
    val message: String,
    val details: String
)

data class DeliverySchemaDetails(
    val mfrUrl: String = "",
    val refUrls: List<String> = emptyList(),
    val partNumber: String = "",
    val tradeName: String = "",
    val retailDesc: String = "",
    val marketingDesc: String = "",
    val itemFeatures: List<String> = emptyList(),
    val withPhrase: String = "",
    val standardApprovals: String = "",
    val productName: String = "",
    val warranty: String = "",
    val productImages: List<String> = emptyList(),
    val specSheetPdf: String = "",
    val manuals: List<String> = emptyList(),
    val countryOfOrigin: String = "US",
    val actualImage: Boolean = true
)

data class EnrichedProductRecord(
    val rawItem: RawCatalogItem,
    val canonicalManufacturer: String,
    val canonicalBrand: String,
    val brandCode: String,
    val classpath: String,
    val unspscCode: String,
    // 5 Distinct Output Formats as per Unilog Content Guidelines
    val invoiceDesc: String,       // <= 40 chars, strict UPPERCASE
    val mobileDesc: String,        // 60 - 80 chars
    val productTitle: String,      // Brand + Series + MPN + Type + Key Attrs
    val longDescription: String,   // Full catalog copy
    val attributes: List<EnrichedAttribute>,
    // 252-column delivery format schema details
    val deliveryDetails: DeliverySchemaDetails = DeliverySchemaDetails(),
    // Quality & Conformance Metrics
    val invoiceCharCount: Int,
    val mobileCharCount: Int,
    val titleCharCount: Int,
    val isInvoiceCompliant: Boolean,
    val isMobileCompliant: Boolean,
    val lovConformancePercentage: Int,
    val confidenceScore: Int,      // 0 - 100%
    val needsHumanReview: Boolean,
    val reviewReasons: List<String> = emptyList(),
    val validationRules: List<ValidationRuleResult> = emptyList()
)

data class BenchmarkSummary(
    val totalProcessed: Int,
    val perfectMatchRate: Float,
    val invoiceRuleCompliance: Float,
    val mobileRuleCompliance: Float,
    val uomStandardCompliance: Float,
    val flaggedForReviewCount: Int,
    val averageConfidenceScore: Float
)
