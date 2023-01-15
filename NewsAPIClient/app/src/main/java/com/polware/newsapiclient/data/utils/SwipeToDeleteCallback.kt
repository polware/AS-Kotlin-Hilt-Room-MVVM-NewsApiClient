package com.polware.newsapiclient.data.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.polware.newsapiclient.R

abstract class SwipeToDeleteCallback(context: Context):
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_icon_delete)
    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val backgroundLeft = ColorDrawable()
    private val backgroundRight = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        /**
         * To disable "swipe" for specific item return 0 here.
         */
        if (viewHolder.adapterPosition == 10)
            return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive
        if (isCanceled) {
            clearCanvas(canvas, itemView.left + dX, itemView.top.toFloat(),
                itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the LEFT background
        backgroundLeft.color = backgroundColor
        backgroundLeft.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        backgroundLeft.draw(canvas)
        // Calculate position of Left delete icon
        val deleteLeftIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteLeftIconMargin = (itemHeight - intrinsicHeight) / 2
        val delete1IconLeft = itemView.right - deleteLeftIconMargin - intrinsicWidth
        val delete1IconRight = itemView.right - deleteLeftIconMargin
        val deleteLeftIconBottom = deleteLeftIconTop + intrinsicHeight
        // Draw the Left delete icon
        deleteIcon!!.setBounds(delete1IconLeft, deleteLeftIconTop, delete1IconRight, deleteLeftIconBottom)
        deleteIcon.draw(canvas)

        // Draw the RIGHT background
        backgroundRight.color = backgroundColor
        backgroundRight.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
        backgroundRight.draw(canvas)
        // Calculate position of Right delete icon
        val deleteRightIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteRightIconMargin = (itemHeight - intrinsicHeight) / 2
        val delete2IconLeft = itemView.left + deleteRightIconMargin - intrinsicWidth
        val delete2IconRight = itemView.left + deleteRightIconMargin
        val deleteRightIconBottom = deleteRightIconTop + intrinsicHeight
        // Draw the Right delete icon
        deleteIcon.setBounds(delete2IconLeft, deleteRightIconTop, delete2IconRight, deleteRightIconBottom)
        deleteIcon.draw(canvas)

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }

}