package de.ritterweb.checkitv01

//
//   Tutorial:  https://www.youtube.com/watch?v=xE8z8wiXz18&t=0s
//   Für die "Dekoration" der REcyclerview wird der in GitHub hinterlegt RecyclerViewSwipeDecorator genutzt.
//    Diese muss vorher im gradle build (App) als dependencie eingetragen werden.
//   Ich verwende noch die alte Version 1.2.3  bei der es reicht sie in die Dependencies einzutragen.
//      Siehe auch die Beschreibung in github:  https://github.com/xabaras/RecyclerViewSwipeDecorator



import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import android.content.Context



import androidx.core.content.ContextCompat
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator








abstract class SwipeGestures(context: Context) :ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {



    val deleteColor = ContextCompat.getColor(context,R.color.deletecolor)    // Achtung, für das R wird in android.R automatisch implementiert. Das ist ein Fehler. Daher wieder aus der Liste löschen, dann funktinoiert das. siehe auch tutorial  ca. 9:50
    val archiveColor = ContextCompat.getColor(context,R.color.archivecolor)
    val deleteIcon = R.drawable.ic_delete
    val archiveIcon = R.drawable.ic_add


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
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeLeftActionIcon(deleteIcon)
            .addSwipeRightBackgroundColor(archiveColor)
            .addSwipeRightActionIcon(archiveIcon)

            .create()
            .decorate()




        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }


//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        TODO("Not yet implemented")
//    }


}