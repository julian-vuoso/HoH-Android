package ar.edu.itba.hci.hoh.dialogs;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import ar.edu.itba.hci.hoh.MainActivity;
import ar.edu.itba.hci.hoh.R;
import ar.edu.itba.hci.hoh.elements.Device;

class DoorDialog extends DeviceDialog {
    private AlertDialog dialog;
    private Button openButton, closeButton, lockButton;

    DoorDialog(Fragment fragment, Device device) {
        super(fragment, device);
    }

    void openDialog() {
        LayoutInflater inflater = fragment.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_device_door, null);
        this.dialog = new AlertDialog.Builder(fragment.getContext()).setView(dialogView).create();
        setDialogHeader(dialogView);

        openButton = dialogView.findViewById(R.id.door_panel_open);
        closeButton = dialogView.findViewById(R.id.door_panel_closed);
        lockButton = dialogView.findViewById(R.id.door_panel_locked);

        if (openButton != null && closeButton != null && lockButton != null) {
            setButtons();
            openButton.setOnClickListener(v -> {
                toggleButton(openButton, true);
                toggleButton(closeButton, false);
                toggleButton(lockButton, false);
                execAction("unlock");
                execAction("open");
            });
            closeButton.setOnClickListener(v -> {
                toggleButton(openButton, false);
                toggleButton(closeButton, true);
                toggleButton(lockButton, false);
                execAction("unlock");
                execAction("close");
            });
            lockButton.setOnClickListener(v -> {
                toggleButton(openButton, false);
                toggleButton(closeButton, false);
                toggleButton(lockButton, true);
                execAction("close");
                execAction("lock");
            });
        }

        this.dialog.show();
    }

    void closeDialog() {
        super.cancelTimer();
        dialog.dismiss();
    }

    private void setButtons() {
        if (device.getState().getLock().equals("locked"))
            toggleButton(lockButton, true);
        else {
            if (device.getState().getStatus().equals("closed"))
                toggleButton(closeButton, true);
            else
                toggleButton(openButton, true);
        }
    }

    void reloadData() {
        Log.e(MainActivity.LOG_TAG, "actualizando");
        setButtons();
    }
}