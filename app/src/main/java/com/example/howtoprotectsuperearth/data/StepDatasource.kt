package com.example.howtoprotectsuperearth.data

import com.example.howtoprotectsuperearth.R
import com.example.howtoprotectsuperearth.model.Step

class StepDatasource {
    fun loadSteps(): List<Step> {
        return listOf<Step>(
            Step(R.drawable.enlist_today, R.string.title1, R.string.step1, R.string.desc1),
            Step(R.drawable.training, R.string.title2, R.string.step2, R.string.desc2),
            Step(R.drawable.destroyer, R.string.title3, R.string.step3, R.string.desc3),
            Step(R.drawable.armor, R.string.title4, R.string.step4, R.string.desc4),
            Step(R.drawable.mission, R.string.title5, R.string.step5, R.string.desc5),
            Step(R.drawable.stratagem, R.string.title6, R.string.step6, R.string.desc6),
            Step(R.drawable.fight, R.string.title7, R.string.step7, R.string.desc7),
            Step(R.drawable.democracy, R.string.title8, R.string.step8, R.string.desc8),
            Step(R.drawable.extract, R.string.title9, R.string.step9, R.string.desc9),
            Step(R.drawable.victory, R.string.title10, R.string.step10, R.string.desc10),
            Step(R.drawable.acquisitions, R.string.title11, R.string.step11, R.string.desc11),
            Step(R.drawable.all, R.string.title12, R.string.step12, R.string.desc12),
            Step(R.drawable.drop, R.string.title13, R.string.step13, R.string.desc13),
            Step(R.drawable.dss, R.string.title14, R.string.step14, R.string.desc14),
            Step(R.drawable.traitor, R.string.title15, R.string.step15, R.string.desc15)

            )
    }
}