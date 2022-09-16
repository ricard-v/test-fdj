package com.mackosoft.core.base.presenter

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BasePresenterFragment<Presenter: BaseCoroutinePresenter>: Fragment {
    constructor() : super()
    constructor(@LayoutRes layoutResId: Int) : super(layoutResId)

    abstract var presenter: Presenter

    override fun onDestroyView() {
        presenter.clear()
        super.onDestroyView()
    }
}