package com.fu.group10.capstone.apps.staffmobileapp.Utils;

import android.view.View;
import android.widget.AbsListView;

/**
 * Created by QuangTV on 5/30/2015.
 */
public class ListViewUtils {
    public static View getViewByPosition(int pos, AbsListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
