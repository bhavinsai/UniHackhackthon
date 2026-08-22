package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BenchmarkSummary
import com.example.model.ComplianceStatus
import com.example.model.EnrichedProductRecord
import com.example.model.RawCatalogItem
import com.example.model.UnilogKnowledgeBase
import com.example.ui.theme.BrandBackground
import com.example.ui.theme.BrandError
import com.example.ui.theme.BrandOnErrorContainer
import com.example.ui.theme.BrandOnPrimaryContainer
import com.example.ui.theme.BrandOnSecondaryContainer
import com.example.ui.theme.BrandOnSurface
import com.example.ui.theme.BrandOnSurfaceVariant
import com.example.ui.theme.BrandOutline
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandPrimaryContainer
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.BrandSecondaryContainer
import com.example.ui.theme.BrandSuccess
import com.example.ui.theme.BrandSurface
import com.example.ui.theme.BrandSurfaceContainerLow
import com.example.ui.theme.BrandSurfaceVariant
import com.example.viewmodel.ProductIntelligenceViewModel
import com.example.viewmodel.UnilogUiState

@Composable
fun UnilogTopHeader(
    onTriggerBatch: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .testTag("unilog_top_header"),
        color = BrandSurface,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BrandPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "PI",
                        color = BrandOnPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Column {
                    Text(
                        text = "Unilog Product Intelligence",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandOnSurface
                    )
                    Text(
                        text = "Industrial Content Enrichment Pipeline",
                        style = MaterialTheme.typography.bodySmall,
                        color = BrandOnSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(BrandSecondaryContainer)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(BrandSuccess)
                    )
                    Text(
                        text = "GROUND TRUTH",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandOnSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun PipelineOverviewScreen(
    state: UnilogUiState,
    viewModel: ProductIntelligenceViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("pipeline_overview_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Ground Truth Benchmark Card
        item {
            GroundTruthBenchmarkCard(benchmark = state.benchmark)
        }

        // 8-Stage Pipeline Interactive Roadmap
        item {
            PipelineRoadmapSection()
        }

        // Row 1 Worked Example Spotlight (Frigidaire Dishwasher)
        item {
            WorkedExampleSpotlightCard(
                onLoadStudio = {
                    viewModel.selectPresetItem(state.groundTruthItems[0])
                    viewModel.selectTab(1)
                }
            )
        }
    }
}

@Composable
fun GroundTruthBenchmarkCard(benchmark: BenchmarkSummary) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ground_truth_benchmark_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "GROUND TRUTH ACCURACY (200 ITEMS)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandOnSurfaceVariant,
                    letterSpacing = 1.sp
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BrandSecondaryContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "252 COLUMNS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandOnSecondaryContainer
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "%.1f%%".format(benchmark.perfectMatchRate),
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Light,
                    color = BrandOnPrimaryContainer,
                    lineHeight = 44.sp
                )
                Text(
                    text = "Strict Rule Pass",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandOnSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(BrandSurfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(benchmark.perfectMatchRate / 100f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(BrandPrimary)
                )
            }

            // 4 Rule Performance Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricPill(
                    title = "Invoice (≤40)",
                    value = "%.0f%%".format(benchmark.invoiceRuleCompliance),
                    isHighlight = true,
                    modifier = Modifier.weight(1f)
                )
                MetricPill(
                    title = "Mobile (60-80)",
                    value = "%.0f%%".format(benchmark.mobileRuleCompliance),
                    isHighlight = false,
                    modifier = Modifier.weight(1f)
                )
                MetricPill(
                    title = "UOM Spacing",
                    value = "%.1f%%".format(benchmark.uomStandardCompliance),
                    isHighlight = false,
                    modifier = Modifier.weight(1f)
                )
                MetricPill(
                    title = "Review Flags",
                    value = "${benchmark.flaggedForReviewCount}",
                    isHighlight = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun MetricPill(
    title: String,
    value: String,
    isHighlight: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (isHighlight) BrandPrimaryContainer else BrandSurfaceContainerLow)
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isHighlight) BrandOnPrimaryContainer else BrandOnSurface
            )
            Text(
                text = title,
                fontSize = 10.sp,
                color = if (isHighlight) BrandOnSecondaryContainer else BrandOutline,
                maxLines = 1
            )
        }
    }
}

@Composable
fun PipelineRoadmapSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pipeline_roadmap_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "8-Stage Industrial Pipeline Architecture",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandOnSurface
                )
                Icon(Icons.Default.AccountTree, contentDescription = null, tint = BrandPrimary, modifier = Modifier.size(20.dp))
            }

            val stages = listOf(
                "1. Input Analysis" to "Discard placeholders (-- Unbranded --) & parse raw abbreviations",
                "2. De-duplication" to "Group supplier variants and resolve redundant vendor records",
                "3. Taxonomy & UNSPSC" to "Classify into leaf nodes (Built-In Dishwashers, Pipe Fittings, Faucets)",
                "4. Attribute Extraction" to "Map dimensions, voltage, pressure class & connection types to LOV",
                "5. Brand Normalization" to "Fuzzy match against 27,000+ UniCat approved brand/manufacturer names",
                "6. Cleansing & UOMs" to "Standardize units ('24 in', not '24in') & convert fractions (0.25 -> 1/4)",
                "7. Multi-Tier Descriptions" to "Generate Invoice (≤40 CAPS), Mobile (60-80), Title & Long copy",
                "8. Quality & Confidence" to "Score against 200 ground truth benchmarks & flag edge cases"
            )

            stages.forEachIndexed { index, (title, desc) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(if (index < 4) BrandPrimaryContainer else BrandSecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandOnPrimaryContainer
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BrandOnSurface
                        )
                        Text(
                            text = desc,
                            fontSize = 11.sp,
                            color = BrandOnSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WorkedExampleSpotlightCard(onLoadStudio: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("worked_example_spotlight_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BrandPrimaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "WORKED GROUND TRUTH: ROW 1 SPOTLIGHT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandOnSecondaryContainer
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandSurface)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("PDSH4816AF", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = BrandPrimary)
                }
            }

            Text(
                text = "Raw Input:",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandOnSecondaryContainer
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BrandSurface)
                    .padding(10.dp)
            ) {
                Text(
                    text = "PDSH4816AF Dishwasher SS - Display Only",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = BrandOnSurface
                )
            }

            Text(
                text = "Enriched 5-Tier Transformation:",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandOnSecondaryContainer
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(BrandSurface)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                TransformationRow(label = "Invoice (≤40 CAPS)", text = "DISHWASHER LEG 5 SST 120V 15A 50-1/4IN")
                TransformationRow(label = "Mobile (60-80)", text = "Rheem Manufacturing FRIGIDAIRE, Dishwasher, Professional Series, PDSH4816AF")
                TransformationRow(label = "Product Title", text = "FRIGIDAIRE® Professional Series PDSH4816AF Dishwasher With CleanBoost™, Leg Mounting, 5-Wash Cycle, Stainless Steel")
            }

            Button(
                onClick = onLoadStudio,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandPrimary,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open in Interactive Enrichment Studio", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun TransformationRow(label: String, text: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = BrandPrimary)
        Text(text = text, fontSize = 11.sp, color = BrandOnSurface)
    }
}

@Composable
fun EnrichmentStudioScreen(
    state: UnilogUiState,
    viewModel: ProductIntelligenceViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("enrichment_studio_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Interactive Enrichment Studio",
                style = MaterialTheme.typography.titleLarge,
                color = BrandOnSurface
            )
            Text(
                text = "Test raw catalog input against Unilog Content Guidelines, Master UOMs, and UniCat Brand standards.",
                style = MaterialTheme.typography.bodyMedium,
                color = BrandOnSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // Preset Selector Row
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "SELECT GROUND TRUTH / HACKATHON SAMPLE:",
                    style = MaterialTheme.typography.labelSmall,
                    color = BrandOutline
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.groundTruthItems) { item ->
                        val isSelected = state.currentRawItem.id == item.id
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.selectPresetItem(item) },
                            label = { Text(item.mfgPartNum, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BrandPrimaryContainer,
                                selectedLabelColor = BrandOnPrimaryContainer,
                                containerColor = BrandSurface,
                                labelColor = BrandOnSurfaceVariant
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = BrandSurfaceVariant,
                                selectedBorderColor = BrandPrimary,
                                enabled = true,
                                selected = isSelected
                            )
                        )
                    }
                }
            }
        }

        // Input Editor Form Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BrandSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "RAW CATALOG INPUT RECORD",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandOnSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = state.customInputMpn,
                            onValueChange = { viewModel.updateCustomFields(it, state.customInputDesc, state.customInputBrand, state.customInputManuf) },
                            label = { Text("Mfg Part Num") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = state.customInputBrand,
                            onValueChange = { viewModel.updateCustomFields(state.customInputMpn, state.customInputDesc, it, state.customInputManuf) },
                            label = { Text("Raw Brand") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    OutlinedTextField(
                        value = state.customInputDesc,
                        onValueChange = { viewModel.updateCustomFields(state.customInputMpn, it, state.customInputBrand, state.customInputManuf) },
                        label = { Text("Raw Part Description (Part_Desc)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = { viewModel.processCustomStudioItem() },
                        enabled = !state.isStudioProcessing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("run_enrichment_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary, contentColor = Color.White)
                    ) {
                        if (state.isStudioProcessing) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Enriching & Validating...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Execute Enrichment Pipeline", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Enriched Record Results
        val record = state.currentEnrichedRecord
        item {
            Text(
                text = "ENRICHED OUTPUT & 5-TIER DESCRIPTIONS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = BrandOnSurfaceVariant
            )
        }

        // 1. Invoice Description Card
        item {
            DescriptionOutputCard(
                title = "1. INVOICE DESCRIPTION (≤40 chars, Strict ALL CAPS)",
                content = record.invoiceDesc,
                charCount = record.invoiceCharCount,
                limitLabel = "Max 40 chars",
                isCompliant = record.isInvoiceCompliant,
                tag = "invoice_desc_card"
            )
        }

        // 2. Mobile Description Card
        item {
            DescriptionOutputCard(
                title = "2. MOBILE DESCRIPTION (60–80 chars)",
                content = record.mobileDesc,
                charCount = record.mobileCharCount,
                limitLabel = "60–80 chars",
                isCompliant = record.isMobileCompliant,
                tag = "mobile_desc_card"
            )
        }

        // 3. Product Title / Short Description Card
        item {
            DescriptionOutputCard(
                title = "3. PRODUCT TITLE / SHORT DESCRIPTION",
                content = record.productTitle,
                charCount = record.titleCharCount,
                limitLabel = "Brand® + Series + MPN + Type + Attrs",
                isCompliant = true,
                tag = "product_title_card"
            )
        }

        // 4. Long Description Card
        item {
            DescriptionOutputCard(
                title = "4. LONG CATALOG DESCRIPTION",
                content = record.longDescription,
                charCount = record.longDescription.length,
                limitLabel = "Full Master Copy",
                isCompliant = true,
                tag = "long_desc_card"
            )
        }

        // 5. Extracted Normalized Attributes Table
        item {
            AttributesTableCard(attributes = record.attributes)
        }

        // 6. 252-Column Delivery Schema & Digital Assets Card
        item {
            DeliverySchemaCard(details = record.deliveryDetails, record = record)
        }

        // 7. Quality & Rule Conformance Checklist
        item {
            RuleValidationAuditCard(rules = record.validationRules, confidenceScore = record.confidenceScore, needsReview = record.needsHumanReview, reasons = record.reviewReasons)
        }
    }
}

@Composable
fun DeliverySchemaCard(details: com.example.model.DeliverySchemaDetails, record: EnrichedProductRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("delivery_schema_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "5. 252-COLUMN DELIVERY & ASSETS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandPrimaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "UniCat Ready",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandOnPrimaryContainer
                    )
                }
            }

            if (details.productImages.isNotEmpty()) {
                Text(
                    text = "Associated Digital Assets:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandOnSurfaceVariant
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(details.productImages) { img ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(BrandSurfaceContainerLow)
                                .border(1.dp, BrandSurfaceVariant, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Description,
                                    contentDescription = null,
                                    tint = BrandPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(img, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = BrandOnSurface)
                            }
                        }
                    }
                }
            }

            if (details.specSheetPdf.isNotBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BrandSurfaceContainerLow)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = BrandSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Column {
                        Text(
                            text = "Specification PDF:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandOnSurfaceVariant
                        )
                        Text(
                            text = details.specSheetPdf,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = BrandOnSurface
                        )
                    }
                }
            }

            if (details.standardApprovals.isNotBlank()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BrandSurfaceContainerLow)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Standard Approvals & Certifications:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandOnSurfaceVariant
                    )
                    Text(
                        text = details.standardApprovals.replace("|", " • "),
                        fontSize = 11.sp,
                        color = BrandOnSurface
                    )
                }
            }

            if (details.mfrUrl.isNotBlank()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BrandSurfaceContainerLow)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Manufacturer Source Reference URL:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandOnSurfaceVariant
                    )
                    Text(
                        text = details.mfrUrl,
                        fontSize = 11.sp,
                        color = BrandPrimary,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun DescriptionOutputCard(
    title: String,
    content: String,
    charCount: Int,
    limitLabel: String,
    isCompliant: Boolean,
    tag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(tag),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isCompliant) Color(0xFFC4F2D0) else Color(0xFFFFDAD6))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "$charCount chars ($limitLabel)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCompliant) Color(0xFF00210B) else Color(0xFF410002)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BrandBackground)
                    .padding(12.dp)
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = BrandOnSurface,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun AttributesTableCard(attributes: List<com.example.model.EnrichedAttribute>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("attributes_table_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "5. EXTRACTED & NORMALIZED ATTRIBUTES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
                Text(
                    text = "${attributes.size} Captured Attributes",
                    fontSize = 11.sp,
                    color = BrandOutline
                )
            }

            attributes.forEach { attr ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BrandSurfaceContainerLow)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = attr.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BrandOnSurface)
                        Text(text = "Raw: ${attr.rawValue}", fontSize = 10.sp, color = BrandOutline)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(BrandPrimaryContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = attr.normalizedValue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandOnPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RuleValidationAuditCard(
    rules: List<com.example.model.ValidationRuleResult>,
    confidenceScore: Int,
    needsReview: Boolean,
    reasons: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("rule_validation_audit_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RULEBOOK COMPLIANCE AUDIT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (confidenceScore >= 90) Color(0xFFC4F2D0) else Color(0xFFFFDAD6))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Confidence: $confidenceScore%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (confidenceScore >= 90) Color(0xFF00210B) else Color(0xFF410002)
                    )
                }
            }

            if (needsReview) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFFDAD6))
                        .padding(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = BrandError, modifier = Modifier.size(18.dp))
                        Text(
                            text = reasons.firstOrNull() ?: "Flagged for human cataloguer review.",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF410002)
                        )
                    }
                }
            }

            rules.forEach { rule ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (rule.status == ComplianceStatus.PASSED) Icons.Default.CheckCircle else Icons.Default.Error,
                        contentDescription = null,
                        tint = if (rule.status == ComplianceStatus.PASSED) BrandSuccess else BrandError,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = rule.ruleName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = BrandOnSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = rule.message,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandOnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun BatchEngineScreen(
    state: UnilogUiState,
    viewModel: ProductIntelligenceViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("batch_engine_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Batch Processing Engine (1,000 Items Scale)",
                style = MaterialTheme.typography.titleLarge,
                color = BrandOnSurface
            )
            Text(
                text = "High-throughput pipeline for catalog ingestion with automated LOV validation & delivery schema generation.",
                style = MaterialTheme.typography.bodyMedium,
                color = BrandOnSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // Run Batch Action Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BrandSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Total Raw Records", fontSize = 11.sp, color = BrandOutline)
                            Text("${state.batchItems.size} Items In Queue", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BrandOnSurface)
                        }

                        Button(
                            onClick = { viewModel.runBatchSimulation() },
                            enabled = !state.isBatchRunning,
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary, contentColor = Color.White)
                        ) {
                            if (state.isBatchRunning) {
                                CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                            } else {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Process Batch")
                            }
                        }
                    }

                    if (state.isBatchRunning) {
                        LinearProgressIndicator(
                            progress = { state.batchProgress },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = BrandPrimary,
                            trackColor = BrandSurfaceVariant
                        )
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            val categories = listOf("All", "Plumbing", "Appliances", "Hydraulics", "Valves")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    val isSelected = state.batchCategoryFilter == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setBatchCategoryFilter(cat) },
                        label = { Text(cat, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BrandPrimaryContainer,
                            selectedLabelColor = BrandOnPrimaryContainer,
                            containerColor = BrandSurface,
                            labelColor = BrandOnSurfaceVariant
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = BrandSurfaceVariant,
                            selectedBorderColor = BrandPrimary,
                            enabled = true,
                            selected = isSelected
                        )
                    )
                }
            }
        }

        // Batch Enriched Items
        val filtered = state.enrichedBatchResults.filter { rec ->
            if (state.batchCategoryFilter == "All") true
            else rec.rawItem.dept?.contains(state.batchCategoryFilter, ignoreCase = true) == true ||
                 rec.classpath.contains(state.batchCategoryFilter, ignoreCase = true)
        }

        items(filtered) { record ->
            BatchRecordItemCard(
                record = record,
                onClick = {
                    viewModel.selectPresetItem(record.rawItem)
                    viewModel.selectTab(1)
                }
            )
        }
    }
}

@Composable
fun BatchRecordItemCard(
    record: EnrichedProductRecord,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("batch_item_${record.rawItem.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(BrandPrimaryContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = record.canonicalBrand,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandOnPrimaryContainer
                        )
                    }

                    Text(
                        text = record.rawItem.mfgPartNum,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandOnSurface
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (record.confidenceScore >= 90) Color(0xFFC4F2D0) else Color(0xFFFFDAD6))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${record.confidenceScore}% Pass",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (record.confidenceScore >= 90) Color(0xFF00210B) else Color(0xFF410002)
                    )
                }
            }

            Text(
                text = record.productTitle,
                style = MaterialTheme.typography.bodyMedium,
                color = BrandOnSurface,
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Invoice: ${record.invoiceDesc}",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = BrandOutline,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Inspect ➔",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
            }
        }
    }
}

@Composable
fun StandardsExplorerScreen(
    state: UnilogUiState,
    viewModel: ProductIntelligenceViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("standards_explorer_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Unilog Master Standards & Controlled LOV",
                style = MaterialTheme.typography.titleLarge,
                color = BrandOnSurface
            )
            Text(
                text = "Reference specifications for UOM abbreviations, trade fraction conversions, and UniCat approved brands.",
                style = MaterialTheme.typography.bodyMedium,
                color = BrandOnSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // Section Tabs
        item {
            val tabTitles = listOf("UOM Standards", "Fraction Table", "Approved Brands", "LOV Specs")
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = BrandSurface,
                contentColor = BrandPrimary,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 11.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }
        }

        // Search Field
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search abbreviations, units, or brands...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = BrandSurface,
                    unfocusedContainerColor = BrandSurface
                )
            )
        }

        when (selectedTab) {
            0 -> {
                // UOM Standards List
                val filtered = UnilogKnowledgeBase.UOM_MAPPINGS.filter {
                    searchQuery.isEmpty() || it.key.contains(searchQuery, ignoreCase = true) || it.value.contains(searchQuery, ignoreCase = true)
                }
                items(filtered.toList()) { (raw, approved) ->
                    StandardItemRow(
                        leftLabel = "Raw: \"$raw\"",
                        rightLabel = "Approved: \"$approved\"",
                        description = "Enforces space between number and unit (e.g. 24 $approved, not 24$approved)"
                    )
                }
            }
            1 -> {
                // Fraction Lookup Table
                val filtered = UnilogKnowledgeBase.DECIMAL_FRACTION_LOOKUP.filter {
                    searchQuery.isEmpty() || it.first.toString().contains(searchQuery) || it.second.contains(searchQuery)
                }
                items(filtered) { (decimal, fraction) ->
                    StandardItemRow(
                        leftLabel = "Decimal: $decimal",
                        rightLabel = "Trade Fraction: $fraction in",
                        description = "Trade buyers search fractions (e.g. 50.25 in converts to 50-1/4 in)"
                    )
                }
            }
            2 -> {
                // Approved Brands
                val filtered = UnilogKnowledgeBase.CANONICAL_BRANDS.filter {
                    searchQuery.isEmpty() || it.key.contains(searchQuery, ignoreCase = true) || it.value.first.contains(searchQuery, ignoreCase = true)
                }
                items(filtered.toList()) { (key, info) ->
                    StandardItemRow(
                        leftLabel = "Approved Brand: ${info.first}",
                        rightLabel = "Code: ${info.third}",
                        description = "Parent Manufacturer: ${info.second} (UniCat 27,000+ db)"
                    )
                }
            }
            3 -> {
                // Industrial Jargon & Abbreviations
                val filtered = UnilogKnowledgeBase.ABBREVIATIONS.filter {
                    searchQuery.isEmpty() || it.key.contains(searchQuery, ignoreCase = true) || it.value.contains(searchQuery, ignoreCase = true)
                }
                items(filtered.toList()) { (abbr, full) ->
                    StandardItemRow(
                        leftLabel = "Abbr: $abbr",
                        rightLabel = "Normalized: $full",
                        description = "Standard controlled vocabulary mapping for fittings and industrial hardware"
                    )
                }
            }
        }
    }
}

@Composable
fun StandardItemRow(
    leftLabel: String,
    rightLabel: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandSurfaceVariant))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = leftLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BrandOnSurface)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandPrimaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(text = rightLabel, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BrandOnPrimaryContainer)
                }
            }
            Text(text = description, fontSize = 11.sp, color = BrandOnSurfaceVariant)
        }
    }
}

@Composable
fun UnilogBottomNav(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .testTag("unilog_bottom_nav"),
        color = BrandSurface,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf(
                Triple(0, "Overview", Icons.Default.Dashboard),
                Triple(1, "Studio", Icons.Default.Build),
                Triple(2, "Batch 1000", Icons.Default.AutoAwesome),
                Triple(3, "Standards", Icons.Default.Rule)
            )

            tabs.forEach { (index, label, icon) ->
                val isSelected = selectedTab == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("unilog_nav_tab_$index")
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(56.dp)
                                .height(30.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(BrandPrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = BrandOnPrimaryContainer,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandOnPrimaryContainer,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier.size(28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = BrandOnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = BrandOnSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
