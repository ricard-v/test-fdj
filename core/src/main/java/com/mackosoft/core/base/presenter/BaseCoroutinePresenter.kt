package com.mackosoft.core.base.presenter

import com.mackosoft.core.base.model.BaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutinePresenter<Model: BaseModel<*>, View>(
    private val mainDispatcher: CoroutineDispatcher,
    protected val model: Model,
    protected val view: WeakReference<View>,
) : CoroutineScope {

    private var job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() {
            // Given that:
            // 1. Fragments can survive their view (and not the other way around)
            // 2. The CoroutineScope's job's lifecycle is linked to the view's lifecycle
            // we may have to recreate a Job when a Fragment is being re-used with a new view.
            if (job.isCancelled) {
                job = SupervisorJob()
            }
            return job + mainDispatcher
        }

    /**
     * To be called whenever a view is no longer valid (eg: destroyed).
     */
    fun clear() {
        cancel()
    }
}