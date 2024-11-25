package com.example.game2d

import android.content.Context
import android.icu.number.Scale
import android.media.MediaPlayer
import com.example.game2d.ui.theme.Background
import com.example.game2d.ui.theme.Boy
import com.example.game2d.ui.theme.Virus2
import com.example.game2d.ui.theme.Virus1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game (val scope: CoroutineScope,val screenW:Int, val screenH: Int,scale: Float,context: Context){
    var counter = 0
    val state = MutableStateFlow(0)
    val background = Background(screenW)
    val boy = Boy(screenH,scale)
    var isPlaying = true
    val virus = Virus1(screenW,screenH,scale)
    val virus2 = Virus1(screenW,screenH,scale)
    var mper1 = MediaPlayer.create(context,R.raw.lastletter)
    var mper2 = MediaPlayer.create(context,R.raw.gameover)


    fun Play(){
        scope.launch {
            //counter = 0
            isPlaying = true
            while (isPlaying) {
                mper1.start()
                delay(40)
                background.Roll()
                if (counter % 3 ==0){
                    boy.Walk()
                    virus.Fly()
                    virus2.Fly()
                    if(boy.getRect().intersect(virus.getRect())) {
                        isPlaying = false
                        mper1.pause()
                        mper2.start()
                    }
                }
                counter++
                state.emit(counter)
            }
        }
    }
    fun Restart(){
        virus.Reset()
        counter = 0
        isPlaying = true
        Play()
    }
}