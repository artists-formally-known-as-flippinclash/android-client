package com.tir38.android.blastermind.utils;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

public class DialogUtils {

    public static final String TAG_PROGRESS_DIALOG = "DialogUtils.progressDialog";

    public static void showLoadingDialog(FragmentManager fragmentManager, String message) {
        if (fragmentManager == null) {
            return;
        }

        Fragment existingDialogFragment = fragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG);
        boolean dialogExists = existingDialogFragment != null;

        DialogFragment updatedDialogFragment = null;
        if (TextUtils.isEmpty(message)) {
            updatedDialogFragment = ProgressDialogFragment.newInstance();
        } else {
            updatedDialogFragment = ProgressDialogFragment.newInstance(message);
        }
        updatedDialogFragment.setCancelable(false);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(updatedDialogFragment, TAG_PROGRESS_DIALOG);
        if (dialogExists) {
            transaction.remove(existingDialogFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    public static void hideLoadingDialog(FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }

        Fragment fragment = fragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG);
        if (fragment instanceof DialogFragment) {
            ((DialogFragment) fragment).dismiss();
        }
    }

    private DialogUtils() {
        // can't instantiate helper class
    }

}