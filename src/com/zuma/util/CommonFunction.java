package com.zuma.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;

import com.zuma.common.MainActivity;

public class CommonFunction {

	public static void alert_NoUsertoken(final Context context) {
		Dialog alert = new AlertDialog.Builder(context)
				.setTitle("������˼��").setMessage("���������ǵ�¼̫�ã������µ�¼��")
				.setPositiveButton("��ת��¼", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(context, MainActivity.class);
						context.startActivity(intent);
						((Activity) context).finish();
					}
				}).setNegativeButton("�˳�����", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				}).create();
		alert.show();
	}
}
