package com.example.howtoprotectsuperearth.data

import com.example.howtoprotectsuperearth.R
import com.example.howtoprotectsuperearth.model.Step

class StepDatasource {
    fun loadSteps(): List<Step> {
        return listOf<Step>(
            Step(R.drawable.enlist_today, R.string.title1, R.string.step1, R.string.desc1),
            )
    }
}