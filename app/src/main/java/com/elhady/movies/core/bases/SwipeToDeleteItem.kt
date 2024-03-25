package com.elhady.movies.core.bases

import android.graphics.Canvas
import android.util.TypedValue
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class SwipeToDeleteItem : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .setSwipeLeftLabelColor(recyclerView.context.getColor(R.color.on_background_38))
            .addSwipeLeftActionIcon(R.drawable.ic_delete)
            .addCornerRadius(TypedValue.COMPLEX_UNIT_DIP,12)
            .setActionIconTint(recyclerView.context.getColor(R.color.on_background_87))
            .addPadding(TypedValue.COMPLEX_UNIT_DIP,16f,0f,16f)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
}
