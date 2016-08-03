package com.ics.bulbControl;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class Controller {

    private PHHueSDK phHueSDK;

    
    
    private static final int MAX_HUE=65535;
    private Controller instance;

    public Controller() {
        this.phHueSDK = PHHueSDK.getInstance();
        this.instance = this;
    }

    public void findBridges() {
        phHueSDK = PHHueSDK.getInstance();
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
    }

    private PHSDKListener listener = new PHSDKListener() {

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {
            AccessPointList accessPointList = new AccessPointList(accessPointsList, instance);
            accessPointList.setVisible(true);
            accessPointList.setLocationRelativeTo(null);  // Centre the AccessPointList Frame
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            // Start the Pushlink Authentication.
            phHueSDK.startPushlinkAuthentication(accessPoint);

        }

        @Override
        public void onBridgeConnected(PHBridge bridge) {
            phHueSDK.setSelectedBridge(bridge);
            phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
            String username = HueProperties.getUsername();
            String lastIpAddress =  bridge.getResourceCache().getBridgeConfiguration().getIpAddress();   
            System.out.println("On connected: IP " + lastIpAddress);
            HueProperties.storeUsername(username);
            HueProperties.storeLastIPAddress(lastIpAddress);
            HueProperties.saveProperties();
        }

        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge arg1) {
        }

        @Override
        public void onConnectionLost(PHAccessPoint arg0) {
        }

        @Override
        public void onConnectionResumed(PHBridge arg0) {
        }

        @Override
        public void onError(int code, final String message) {
            System.out.println(message);
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {  
            for (PHHueParsingError parsingError: parsingErrorsList) {
                System.out.println("ParsingError : " + parsingError.getMessage());
            }
        } 
    };

    public PHSDKListener getListener() {
        return listener;
    }

    public void setListener(PHSDKListener listener) {
        this.listener = listener;
    }

    public void randomLights() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        PHBridgeResourcesCache cache = bridge.getResourceCache();

        List<PHLight> allLights = cache.getAllLights();
        Random rand = new Random();

        for (PHLight light : allLights) {
            PHLightState lightState = new PHLightState();
            lightState.setHue(rand.nextInt(MAX_HUE));
            bridge.updateLightState(light, lightState); // If no bridge response is required then use this simpler form.
        }
    }
    
    public void changeColor(int r, int g, int b){
    	Color lightColour = new Color(r, g, b);
        PHLightState lightState = new PHLightState();
        float xy[] = PHUtilities.calculateXYFromRGB(lightColour.getRed(), lightColour.getGreen(), lightColour.getBlue(), "LCT001");
        lightState.setX(xy[0]);
        lightState.setY(xy[1]);
        
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        
        for(PHLight light : allLights){
        	 bridge.updateLightState(light, lightState);
        	 try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public void turnOnOrOffBulb(boolean onOrOff){
    	PHLightState lightState = new PHLightState();
    	lightState.setOn(onOrOff);
    	
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        
        for(PHLight light : allLights){
        	 bridge.updateLightState(light, lightState);
        	 try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public void changeBrightness(int brightness){
    	PHLightState lightState = new PHLightState();
    	lightState.setBrightness(brightness);
    	
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        
        for(PHLight light : allLights){
        	 bridge.updateLightState(light, lightState);
        	 try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    /**
     * Connect to the last known access point.
     * This method is triggered by the Connect to Bridge button but it can equally be used to automatically connect to a bridge.
     * 
     */
    public boolean connectToLastKnownAccessPoint() {
        String username = HueProperties.getUsername();
        String lastIpAddress =  HueProperties.getLastConnectedIP();     
        
        if (username==null || lastIpAddress == null) {
        	System.out.println("Missing Last Username or Last IP.  Last known connection not found.");
            return false;
        }
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(lastIpAddress);
        accessPoint.setUsername(username);
        phHueSDK.connect(accessPoint);
        return true;
    }
}
