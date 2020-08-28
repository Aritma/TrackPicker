package com.aritma.trackpicker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_card_picker.*


class CardPicker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_picker)

        var menuHidden = false
        val trackNum = intent.getIntExtra("track_num", 2)
        var trackList = mutableListOf(0)


        fun toggleMenu() {
            if (menuHidden) {
                arrayOf(ib_back, ib_reset_cards, tv_back, tv_reset_cards).iterator().forEach {
                        it.visibility = View.VISIBLE
                    }
                ib_show_menu.rotation = 180F
                tv_show_menu.text = getText(R.string.hide_menu)
                menuHidden = false
            } else {
                arrayOf(ib_back, ib_reset_cards, tv_back, tv_reset_cards).iterator().forEach {
                        it.visibility = View.GONE
                    }
                ib_show_menu.rotation = 0F
                tv_show_menu.text = getText(R.string.show_menu)
                menuHidden = true
            }
        }


        fun setCardSize() {
            val sideRatio = 1.4
            val multiplier = when (trackNum) {
                0 -> 0.4
                1 -> 0.4
                2 -> 0.4
                3 -> 0.3
                4 -> 0.3
                else -> 0.3
            }
            val cardWidth = (this.resources.displayMetrics.widthPixels*multiplier).toInt()

            arrayOf(
                bt_card_1,
                bt_card_2,
                bt_card_3,
                bt_card_4,
                bt_card_5,
                bt_card_6
            ).iterator().forEach {
                it.layoutParams.width = cardWidth
                it.layoutParams.height = (cardWidth*sideRatio).toInt()
            }

        }


        fun setCardVisibility() {
            when (trackNum) {
                0 -> {
                    bt_card_3.visibility = View.GONE
                    bt_card_4.visibility = View.GONE
                    bt_card_5.visibility = View.GONE
                    bt_card_6.visibility = View.GONE
                }
                1 -> {
                    bt_card_2.visibility = View.GONE
                    bt_card_5.visibility = View.GONE
                    bt_card_6.visibility = View.GONE
                }
                2 -> {
                    bt_card_3.visibility = View.GONE
                    bt_card_6.visibility = View.GONE
                }
                3 -> {
                    bt_card_6.visibility = View.GONE
                }
                4 -> {
                    // This is default -> all visible
                }
            }
        }


        fun resetCards() {
            arrayOf(
                bt_card_1 as Button,
                bt_card_2 as Button,
                bt_card_3 as Button,
                bt_card_4 as Button,
                bt_card_5 as Button,
                bt_card_6 as Button
            ).iterator().forEach {
                it.background = ContextCompat.getDrawable(this, R.color.colorCardHidden)
                it.text = getText(R.string.card_hidden)
                it.isClickable = true
            }

            trackList = 1.rangeTo(trackNum+2).toMutableList()
            trackList.shuffle()
        }


        fun toggleCard(card: Button) {
            val num = trackList.removeAt(0)

            card.background = ContextCompat.getDrawable(this, R.color.colorCardPicked)
            card.text = num.toString()
            card.isClickable = false
        }


        setCardSize()
        setCardVisibility()
        resetCards()
        toggleMenu()

        ib_show_menu.setOnClickListener {
            toggleMenu()
        }

        ib_reset_cards.setOnClickListener {
            resetCards()
        }

        ib_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bt_card_1.setOnClickListener { toggleCard(bt_card_1 as Button) }
        bt_card_2.setOnClickListener { toggleCard(bt_card_2 as Button) }
        bt_card_3.setOnClickListener { toggleCard(bt_card_3 as Button) }
        bt_card_4.setOnClickListener { toggleCard(bt_card_4 as Button) }
        bt_card_5.setOnClickListener { toggleCard(bt_card_5 as Button) }
        bt_card_6.setOnClickListener { toggleCard(bt_card_6 as Button) }
    }
}