package com.example.widget;

import com.example.mymeituan.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeletDialog extends Dialog {

	public DeletDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public DeletDialog(Context context, int theme) {
		super(context, theme);
	}

	public DeletDialog(Context context) {
		super(context);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public DeletDialog create() {
			LayoutInflater inflater = LayoutInflater.from(context);
			final DeletDialog dialog = new DeletDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_indent_delet, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			// ((TextView)layout.findViewById(R.id.delet_dialog_title)).setText(title);
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.btn_delet_dialog_delet))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.btn_delet_dialog_delet))
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				} else {
					layout.findViewById(R.id.btn_delet_dialog_delet)
							.setVisibility(View.GONE);
				}
			}
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.btn_delet_dialog_cancle))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.btn_delet_dialog_cancle))
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				} else {
					layout.findViewById(R.id.btn_delet_dialog_cancle)
							.setVisibility(View.GONE);
				}

			}
			if (message != null) {
				((TextView) layout.findViewById(R.id.tv_delet_dialog_message))
						.setText(message);
			} else if (contentView != null) {
				((LinearLayout) layout
						.findViewById(R.id.ll_delet_dialog_content))
						.removeAllViews();
				((LinearLayout) layout
						.findViewById(R.id.ll_delet_dialog_content)).addView(
						contentView, new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
			}
			dialog.setContentView(layout);
			return dialog;

		}
	}
}
