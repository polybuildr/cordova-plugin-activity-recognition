package cordova.plugin.activity.recognition;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.ActivityRecognitionClient;

public class ActivityRecognitionInit implements ConnectionCallbacks, OnConnectionFailedListener
{
	public GoogleApiClient mApiClient;
	private bool Connected;
	private PendingIntent pendingIntent;
 
	
	public ActivityRecognitionInit()
	{
		Connected = false;
		mApiClient = new GoogleApiClient.Builder(this)
            .addApi(ActivityRecognition.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();
 
		mApiClient.connect();
	}
	
	public bool StartRequestingActivity(int Interval)
	{
		if(Connected)
		{
			Intent intent = new Intent( this, ActivityRecognitionIntentService.class );
			pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
			ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, Interval, pendingIntent );
			return true;
		}
		else 
			return false;
		
	}
	
	public void RemoveActivityUpdates() 
	{
		ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mApiClient, pendingIntent).setResultCallback(this);
	}
 
    @Override
    public void onConnected(@Nullable Bundle bundle) 
	{
        Connected = true; 
    }
 
    @Override
    public void onConnectionSuspended(int i)
	{
 
    }
 
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
	{
 
    }
}