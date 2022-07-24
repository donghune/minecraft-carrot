package io.github.donghune.api.mccoroutine

import io.github.donghune.api.plugin
import org.bukkit.Bukkit
import java.time.Duration
import java.time.LocalDateTime

open class ExpandableKBukkitScheduler {

    private var onBukkitStart: suspend BukkitSchedulerController.() -> Unit = {}
    private var onBukkitDuringSec: suspend BukkitSchedulerController.(Int) -> Unit = { _ -> }
    private var onBukkitStop: suspend BukkitSchedulerController.() -> Unit = {}

    private var _isRunning = false
    val isRunning
        get() = _isRunning

    private var _startTime = LocalDateTime.now()
    val startTime
        get() = _startTime

    private var _finishTime = LocalDateTime.now()
    val finishTime
        get() = _finishTime

    private var _duringTime = _startTime

    val totalTime
        get() = Duration.between(_startTime, _finishTime).seconds.toInt()

    val leftTime
        get() = Duration.between(_duringTime, _finishTime).seconds.toInt()

    private var task: CoroutineTask? = null

    fun getTaskId(): Int? {
        return task?.currentTask?.taskId
    }

    fun start(time: Int) {
        _startTime = LocalDateTime.now()
        _duringTime = LocalDateTime.now()
        _finishTime = _startTime.plusSeconds(time.toLong())
        _isRunning = true
        task = Bukkit.getScheduler().schedule(plugin) {
            onBukkitStart()
            while (true) {
                if (_startTime.isAfter(_finishTime)) {
                    break
                }
                onBukkitDuringSec(leftTime.toInt())
                _duringTime = _duringTime.plusSeconds(1L)
                waitFor(20L)
            }
            onBukkitStop()
        }
    }

    fun stop() {
        _isRunning = false
        task?.cancel()
        Bukkit.getScheduler().schedule(plugin) {
            onBukkitStop()
        }
    }

    fun addTime(time: Int) {
        _finishTime = _finishTime.plusSeconds(time.toLong())
    }

    fun removeTime(time: Int) {
        _finishTime = _finishTime.minusSeconds(time.toLong())
    }

    fun cancel() {
        task?.cancel()
        _isRunning = false
    }

    fun onStart(function: suspend BukkitSchedulerController.() -> Unit) {
        onBukkitStart = function
    }

    fun onDuringSec(function: suspend BukkitSchedulerController.(Int) -> Unit) {
        onBukkitDuringSec = function
    }

    fun onStop(function: suspend BukkitSchedulerController.() -> Unit) {
        onBukkitStop = function
    }
}