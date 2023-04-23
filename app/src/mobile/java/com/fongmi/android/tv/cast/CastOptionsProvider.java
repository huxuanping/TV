package com.fongmi.android.tv.cast;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fongmi.android.tv.R;
import com.fongmi.android.tv.ui.activity.MainActivity;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.cast.framework.media.MediaIntentReceiver;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.images.WebImage;

import java.util.Arrays;
import java.util.List;

public class CastOptionsProvider implements OptionsProvider {

    @NonNull
    @Override
    public CastOptions getCastOptions(Context context) {
        NotificationOptions notificationOptions = new NotificationOptions.Builder().setActions(Arrays.asList(MediaIntentReceiver.ACTION_SKIP_NEXT, MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK, MediaIntentReceiver.ACTION_STOP_CASTING), new int[]{1, 2}).setTargetActivityClassName(MainActivity.class.getName()).build();
        CastMediaOptions mediaOptions = new CastMediaOptions.Builder().setImagePicker(new ImagePickerImpl()).setNotificationOptions(notificationOptions).setExpandedControllerActivityClassName(MainActivity.class.getName()).build();
        LaunchOptions launchOptions = new LaunchOptions.Builder().setAndroidReceiverCompatible(true).build();
        return new CastOptions.Builder().setLaunchOptions(launchOptions).setReceiverApplicationId(context.getString(R.string.app_id)).setCastMediaOptions(mediaOptions).build();
    }

    @Nullable
    @Override
    public List<SessionProvider> getAdditionalSessionProviders(@NonNull Context context) {
        return null;
    }

    private static class ImagePickerImpl extends ImagePicker {

        @Override
        public WebImage onPickImage(MediaMetadata mediaMetadata, @NonNull ImageHints hints) {
            if (mediaMetadata == null || !mediaMetadata.hasImages()) return null;
            return mediaMetadata.getImages().get(0);
        }
    }
}