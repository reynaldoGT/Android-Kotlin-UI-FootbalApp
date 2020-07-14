package com.dosan.baseballui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.slide_layout.view.*

class SliderAdapter(var context: Context) : PagerAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    var slider_backgroud: IntArray =
        intArrayOf(R.drawable.gradient, R.drawable.gradient2, R.drawable.gradient1)
    var sliderImages: IntArray = intArrayOf(
        R.drawable.ic_ball_soccer,
        R.drawable.ic_soccer_ball_2,
        R.drawable.ic_soccer_feet
    )
    var slider_heading: IntArray = intArrayOf(
        R.string.intro_bienvenida,
        R.string.intro_disfruta,
        R.string.intro_redsocial
    )
    var slider_descriptions: IntArray = intArrayOf(
        R.string.intro_bienvenida_des,
        R.string.intro_disfruta_des,
        R.string.intro_redsocial_des
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as (RelativeLayout)
    }

    override fun getCount(): Int {
        return slider_heading.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = layoutInflater.inflate(R.layout.slide_layout, container, false)

        view.slideLayoutRelative.setBackgroundResource(slider_backgroud[position])
        view.slideLayotImageView.setImageResource(sliderImages[position])
        view.SlideLayoutTv1.setText(slider_heading[position])
        view.SlideLayoutTv2.setText(slider_descriptions[position])

        if (position == 0) {
            view.slideLayoutTvPoint1.setTextColor(context.resources.getColor(R.color.colorWhite))
            view.slideLayoutTvPoint2.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint3.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
        }
        if (position == 1) {
            view.slideLayoutTvPoint1.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint2.setTextColor(context.resources.getColor(R.color.colorWhite))
            view.slideLayoutTvPoint3.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
        }
        if (position == 2) {
            view.slideLayoutTvPoint1.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint2.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint3.setTextColor(context.resources.getColor(R.color.colorWhite))
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as (RelativeLayout))
    }
}