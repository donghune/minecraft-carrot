package io.github.donghune.api.mccoroutine

import io.github.donghune.api.plugin
import org.bukkit.Bukkit

open class KBukkitScheduler {

    private var onBukkitStart: suspend BukkitSchedulerController.() -> Unit = {}
    private var onBukkitDuringSec: suspend BukkitSchedulerController.(Int) -> Unit = { _ -> }
    private var onBukkitStop: suspend BukkitSchedulerController.() -> Unit = {}

    private var _isRunning = false
    val isRunning
        get() = _isRunning

    private var _totalTime = 0
    val totalTime
        get() = _totalTime

    private var _during = 0
    val during
        get() = _during

    val leftSec: Int
        get() = totalTime - during

    private var task: CoroutineTask? = null

    fun getTaskId(): Int? {
        return task?.currentTask?.taskId
    }

    fun start(time: Int) {
        _totalTime = time
        _during = 0
        _isRunning = true
        task = Bukkit.getScheduler().schedule(plugin) {
            onBukkitStart()
            for (i in 0..totalTime) {
                _during = i
                onBukkitDuringSec(leftSec)
                waitFor(20)
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