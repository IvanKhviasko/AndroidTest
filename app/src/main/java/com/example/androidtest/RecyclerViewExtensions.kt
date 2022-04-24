
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addPaginationScrollListener(
    layoutManager: LinearLayoutManager,
    itemsToLoad: Int,
    onLoadMore: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            if (dy != 0 && totalItemCount <= (lastVisibleItem + itemsToLoad)) {
                recyclerView.post(onLoadMore)
            }
        }
    })
}