package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.BenchmarkSummary
import com.example.model.EnrichedProductRecord
import com.example.model.RawCatalogItem
import com.example.model.UnilogEnrichmentEngine
import com.example.model.UnilogKnowledgeBase
import com.example.model.UnilogSampleData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UnilogUiState(
    val selectedTab: Int = 0, // 0: Overview & Pipeline, 1: Enrichment Studio, 2: Batch Engine, 3: Rulebook & LOV
    // Ground Truth & Benchmarks
    val benchmark: BenchmarkSummary = UnilogSampleData.INITIAL_BENCHMARK,
    val groundTruthItems: List<RawCatalogItem> = UnilogSampleData.GROUND_TRUTH_SAMPLES,
    // Interactive Studio State
    val currentRawItem: RawCatalogItem = UnilogSampleData.GROUND_TRUTH_SAMPLES[0],
    val currentEnrichedRecord: EnrichedProductRecord = UnilogEnrichmentEngine.enrichRecord(UnilogSampleData.GROUND_TRUTH_SAMPLES[0]),
    val customInputMpn: String = "PDSH4816AF",
    val customInputDesc: String = "PDSH4816AF Dishwasher SS - Display Only",
    val customInputBrand: String = "FRIGIDAIRE",
    val customInputManuf: String = "Rheem Manufacturing Company",
    val isStudioProcessing: Boolean = false,
    // Batch Processing State
    val batchItems: List<RawCatalogItem> = UnilogSampleData.GROUND_TRUTH_SAMPLES + UnilogSampleData.RAW_1000_SAMPLES,
    val enrichedBatchResults: List<EnrichedProductRecord> = (UnilogSampleData.GROUND_TRUTH_SAMPLES + UnilogSampleData.RAW_1000_SAMPLES).map {
        UnilogEnrichmentEngine.enrichRecord(it)
    },
    val isBatchRunning: Boolean = false,
    val batchProgress: Float = 1.0f,
    val batchCategoryFilter: String = "All",
    val batchStatusFilter: String = "All", // "All", "Flagged Review", "Passed 100%"
    val selectedBatchRecordForDetail: EnrichedProductRecord? = null,
    // Standards Explorer State
    val standardsSearchQuery: String = "",
    val activeRulebookSection: Int = 0, // 0: UOM Standards, 1: Fraction Conversions, 2: Approved Brands, 3: LOV Specs
    val snackbarMessage: String? = null
)

class ProductIntelligenceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UnilogUiState())
    val uiState: StateFlow<UnilogUiState> = _uiState.asStateFlow()

    fun selectTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = index)
    }

    fun selectPresetItem(item: RawCatalogItem) {
        val enriched = UnilogEnrichmentEngine.enrichRecord(item)
        _uiState.value = _uiState.value.copy(
            currentRawItem = item,
            currentEnrichedRecord = enriched,
            customInputMpn = item.mfgPartNum,
            customInputDesc = item.partDesc,
            customInputBrand = item.unilogBrand ?: item.e1Brand ?: "",
            customInputManuf = item.partManuf ?: "",
            snackbarMessage = "Loaded preset: ${item.mfgPartNum}"
        )
    }

    fun updateCustomFields(mpn: String, desc: String, brand: String, manuf: String) {
        _uiState.value = _uiState.value.copy(
            customInputMpn = mpn,
            customInputDesc = desc,
            customInputBrand = brand,
            customInputManuf = manuf
        )
    }

    fun processCustomStudioItem() {
        val raw = RawCatalogItem(
            id = "custom_${System.currentTimeMillis()}",
            mfgPartNum = _uiState.value.customInputMpn.trim().ifEmpty { "GEN-PART-01" },
            partDesc = _uiState.value.customInputDesc.trim().ifEmpty { "General Industrial Part" },
            unilogBrand = _uiState.value.customInputBrand.trim(),
            partManuf = _uiState.value.customInputManuf.trim(),
            dept = "Industrial",
            classCategory = "MRO",
            fineCategory = "Components"
        )

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isStudioProcessing = true)
            delay(500)
            val enriched = UnilogEnrichmentEngine.enrichRecord(raw)
            _uiState.value = _uiState.value.copy(
                isStudioProcessing = false,
                currentRawItem = raw,
                currentEnrichedRecord = enriched,
                snackbarMessage = "Enrichment pipeline executed successfully!"
            )
        }
    }

    fun runBatchSimulation() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isBatchRunning = true, batchProgress = 0.0f)
            val total = _uiState.value.batchItems.size
            val results = mutableListOf<EnrichedProductRecord>()

            for (i in 0 until total) {
                results.add(UnilogEnrichmentEngine.enrichRecord(_uiState.value.batchItems[i]))
                if (i % 5 == 0) {
                    _uiState.value = _uiState.value.copy(batchProgress = (i + 1).toFloat() / total)
                    delay(30)
                }
            }

            val passedCount = results.count { !it.needsHumanReview }
            val flaggedCount = results.count { it.needsHumanReview }

            _uiState.value = _uiState.value.copy(
                isBatchRunning = false,
                batchProgress = 1.0f,
                enrichedBatchResults = results,
                benchmark = _uiState.value.benchmark.copy(
                    totalProcessed = total,
                    perfectMatchRate = (passedCount.toFloat() / total) * 100f,
                    flaggedForReviewCount = flaggedCount
                ),
                snackbarMessage = "Processed $total catalog items across 252 delivery attributes."
            )
        }
    }

    fun setBatchCategoryFilter(category: String) {
        _uiState.value = _uiState.value.copy(batchCategoryFilter = category)
    }

    fun setBatchStatusFilter(status: String) {
        _uiState.value = _uiState.value.copy(batchStatusFilter = status)
    }

    fun setSelectedBatchRecordForDetail(record: EnrichedProductRecord?) {
        _uiState.value = _uiState.value.copy(selectedBatchRecordForDetail = record)
    }

    fun setStandardsSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(standardsSearchQuery = query)
    }

    fun setActiveRulebookSection(section: Int) {
        _uiState.value = _uiState.value.copy(activeRulebookSection = section)
    }

    fun clearSnackbar() {
        _uiState.value = _uiState.value.copy(snackbarMessage = null)
    }
}
