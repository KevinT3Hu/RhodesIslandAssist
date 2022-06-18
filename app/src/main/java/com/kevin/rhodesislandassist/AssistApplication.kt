package com.kevin.rhodesislandassist

import android.app.Application
import com.kevin.rhodesislandassist.util.AssetLoader

class AssistApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        DataSetRepository.gameItemDataSet= AssetLoader.loadGameItems(this)
        DataSetRepository.gameStageDataSet= AssetLoader.loadGameStages(this)
    }
}