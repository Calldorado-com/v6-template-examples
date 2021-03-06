//
//  Created by Calldorado Team on August 24th 2017.
//  Copyright © 2017 Calldorado ApS. All rights reserved.
//
//  Licensed under the Calldorado SDK Template License Version 1;
//  you must comply with this license in order to use this file.
//  You may obtain a copy of the License at the following URL:
//  https://github.com/Calldorado-com/calldorado-template-examples
//

package com.your.package;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.calldorado.android.ui.views.custom.CalldoradoCustomView;
import com.your.package.R;


public class AftercallCustomView extends CalldoradoCustomView {

    public AftercallCustomView(Context context) {
        super(context);
    }

    @Override
    public View getRootView() {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lp);

        final LinearLayout show = (LinearLayout) inflate(getContext(),
                R.layout.custom_view_show_message, new LinearLayout(getContext()));

        final LinearLayout write = (LinearLayout) inflate(getContext(),
                R.layout.custom_view_write_message, new LinearLayout(getContext()));
        
        String text = (String) findItem(getPhoneNumber(), "");

        if (text.isEmpty())
            show.setVisibility(GONE);
        else
            write.setVisibility(GONE);

        final TextView lastMessage = (TextView) show.findViewById(R.id.last_message);
        lastMessage.setText(text);

        ImageView quickReply = (ImageView) show.findViewById(R.id.quick_reply);
        quickReply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // TODO your code here
                    write.setVisibility(VISIBLE);
                    show.setVisibility(GONE);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ImageView openApp = (ImageView) show.findViewById(R.id.open_conversation);
        openApp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // TODO your code here
                    showFeedbackMessage("Go to app!");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final TextView writeMessage = (TextView) write.findViewById(R.id.write_message);

        TextView sendQuickReply = (TextView) write.findViewById(R.id.send_quick_reply);
        sendQuickReply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // TODO your code here
                    String currentMessage = writeMessage.getText().toString();
                    if (!currentMessage.isEmpty()) {
                        saveItem(getPhoneNumber(), currentMessage);
                        lastMessage.setText(currentMessage);

                        write.setVisibility(GONE);
                        show.setVisibility(VISIBLE);

                        Activity activity = (Activity) getCalldoradoContext();
                        if (activity.getCurrentFocus() != null) { // hide keyboard
                            InputMethodManager imm = (InputMethodManager)
                                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(), 0);
                        }

                        showFeedbackMessage("Message sent!");
                    }
                    else {
                        showFeedbackMessage("Message is empty.");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ll.addView(show);
        ll.addView(write);

        return ll;
    }
}
