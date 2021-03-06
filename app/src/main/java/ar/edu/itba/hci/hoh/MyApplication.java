package ar.edu.itba.hci.hoh;

import android.app.Application;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import ar.edu.itba.hci.hoh.api.Api;
import ar.edu.itba.hci.hoh.elements.DeviceState;
import ar.edu.itba.hci.hoh.repositories.DeviceRepository;
import ar.edu.itba.hci.hoh.repositories.DeviceTypeRepository;
import ar.edu.itba.hci.hoh.repositories.RoomRepository;
import ar.edu.itba.hci.hoh.repositories.RoutineRepository;

public class MyApplication extends Application {
    private static MyApplication instance;
    private RoomRepository roomRepository;
    private DeviceRepository deviceRepository;
    private DeviceTypeRepository deviceTypeRepository;
    private RoutineRepository routineRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create API instance, no more context needed
        Api.getInstance(this);

        roomRepository = RoomRepository.getInstance(this);
        deviceRepository = DeviceRepository.getInstance(this);
        deviceTypeRepository = DeviceTypeRepository.getInstance(this);
        routineRepository = RoutineRepository.getInstance(this);
        instance = this;
    }

    public synchronized static MyApplication getInstance() {
        return instance;
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    public DeviceTypeRepository getDeviceTypeRepository() {
        return deviceTypeRepository;
    }

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    // TODO: VER COMO HACER PARA QUE EL TOAST NO SE PISE CON EL BOTTOM NAV
    // TODO: VER COMO HACER PARA PARSEAR EL ERROR (Y TRADUCIRLO SI HACE FALTA)
    public static void makeToast(String message) {
        Toast.makeText(instance, message, Toast.LENGTH_SHORT).show();
    }

    public static int getDrawableFromString(String drawableName) {
        return instance.getResources().getIdentifier(drawableName, "drawable", instance.getPackageName());
    }

    public static float getDeviceCardWidth() {
        return instance.getResources().getDimension(R.dimen.device_card_width);
    }

    // TODO: REVISAR COLORES
    public static int getCardBackgroundColor(DeviceState state) {
        if (state == null || state.getStatus() == null) return ContextCompat.getColor(instance, R.color.colorDevCardBackgroundLight);

        switch (state.getStatus()) {
            case "opened":
            case "on":
            case "playing":
                return ContextCompat.getColor(instance, R.color.colorDevCardBackgroundLight);
            case "off":
            case "paused":
            case "closing":
            case "opening":
                return ContextCompat.getColor(instance, R.color.colorDevCardBackgroundMedium);
            case "closed":
                if (state.getLock() != null && state.getLock().equals("unlocked"))
                    return ContextCompat.getColor(instance, R.color.colorDevCardBackgroundMedium);
        }
        // For closed && !unlocked AND for stopped
        return ContextCompat.getColor(instance, R.color.colorDevCardBackgroundDark);
    }
}
