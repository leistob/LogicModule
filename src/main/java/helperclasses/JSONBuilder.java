package helperclasses;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class to build a JSON String according to the CSV guidelines.
 * <p>
 * JSON with basic structure and all pairs it HAS to have
 * {    "jsonrpc": "2.0",
 * "method": <method>,
 * "module": <module>,
 * "params": {
 * "bundle":<bundle>,
 * }
 * }
 */
public class JSONBuilder {

    public static final String module = "gesture";

    public static final String VERSION_JSON = "jsonrpc";
    public static final String MODULE = "module";
    public static final String METHOD = "method";
    public static final String PARAMS = "params";

    public static final String TIMESTAMP = "timestamp";
    public static final String BUNDLE = "bundle";
    public static final String POSITION = "position";
    public static final String STATUS = "status";
    public static final String HAND = "hand";
    public static final String SPEED = "speed";
    public static final String ROTATION = "rotation";

    public static final String HAND_LEFT = "hand_left";
    public static final String HAND_RIGHT = "hand_right";
    public static final String MOVE = "move";
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_INACTIVE = "inactive";
    public static final String POSITION_X = "pos_x";
    public static final String POSITION_Y = "pos_y";
    public static final String POSITION_Z = "pos_z";
    public static final String ROTATION_X = "rot_x";
    public static final String ROTATION_Y = "rot_y";
    public static final String ROTATION_Z = "rot_z";

    public static final String CHANGE_ALL_COLORS = "change_all_colors";
    public static final String CHANGE_COLOR_BY_ID = "change_color_by_id";
    public static final String ON = "on";
    public static final String OFF = "off";
    public static final String FLOW = "flow";

    public static final String COLOR_RED = "red";
    public static final String COLOR_GREEN = "green";
    public static final String COLOR_BLUE = "blue";

    public static final String DEFLECTION = "deflection";
    public static final String DEFLECTION_X = "def_x";
    public static final String DEFLECTION_Y = "def_y";
    public static final String DEFLECTION_Z = "def_z";

    public static final String ORIENTATION = "orientation";
    public static final String PITCH = "pitch";
    public static final String YAW = "yaw";
    public static final String ROLL = "roll";

    public static final String STATUS_DEACTIVATE = "deactivate";

    public static final String INIT = "INIT";


    public static final String MODULE_GESTURE = "gesture";
    public static final String MODULE_VOICE = "voice";
    public static final String MODULE_UI = "ui";
    public static final String MODULE_SMART_PHONE = "smartphone";
    public static final String MODULE_ANIMATION = "animation";

    public static final String DEFAULT_JSON_VERSION = "2.0";

    private JSONObject jsonObject;

    public JSONBuilder() {
        jsonObject = new JSONObject();
        jsonObject.put(VERSION_JSON, DEFAULT_JSON_VERSION);
        jsonObject.put(PARAMS, new JSONObject());
    }

    public JSONBuilder(String toJson) {
        this.jsonObject = new JSONObject(toJson);
    }

    public JSONBuilder setJSONVersion(String version) {
        jsonObject.put(VERSION_JSON, version);
        return this;
    }

    public JSONBuilder setMethod(String method) {
        jsonObject.put(METHOD, method);
        return this;
    }

    public JSONBuilder setModule(String module) {
        jsonObject.put(MODULE, module);
        return this;
    }

    public JSONBuilder setBundle(String bundle) {
        jsonObject.getJSONObject(PARAMS).put(BUNDLE, bundle);
        return this;
    }

    public JSONBuilder setTimestamp(String timestamp) {
        jsonObject.getJSONObject(PARAMS).put(TIMESTAMP, timestamp);
        return this;
    }

    public JSONBuilder setPosition(String position) {
        jsonObject.getJSONObject(PARAMS).put(POSITION, position);
        return this;
    }

    public JSONBuilder setStatus(String status) {
        jsonObject.getJSONObject(PARAMS).put(STATUS, status);
        return this;
    }

    public JSONBuilder setPosition(double x, double y, double z) {
        jsonObject.getJSONObject(PARAMS).put(POSITION, new JSONObject());
        jsonObject.getJSONObject(PARAMS).getJSONObject(POSITION).put(POSITION_X, x)
                .put(POSITION_Y, y)
                .put(POSITION_Z, z);
        return this;
    }

    public JSONBuilder setRotation(double x, double y, double z) {
        jsonObject.getJSONObject(PARAMS).put(ROTATION, new JSONObject());
        jsonObject.getJSONObject(PARAMS).getJSONObject(ROTATION)
                .put(ROTATION_X, x)
                .put(ROTATION_Y, y)
                .put(ROTATION_Z, z);
        return this;
    }

    public JSONBuilder setSpeed(double speed) {
        jsonObject.getJSONObject(PARAMS).put(SPEED, String.valueOf(speed));
        return this;
    }

    public JSONBuilder setHand(String hand) {
        jsonObject.getJSONObject(PARAMS).put(HAND, hand);
        return this;
    }

    public String getSpeed() {
        return jsonObject.getJSONObject(PARAMS).getString(SPEED);
    }

    public String getHand() {
        return jsonObject.getJSONObject(PARAMS).getString(HAND);
    }

    public List<String> getPosition() {
        String x = jsonObject.getJSONObject(PARAMS).getJSONObject(POSITION).getString(POSITION_X);
        String y = jsonObject.getJSONObject(PARAMS).getJSONObject(POSITION).getString(POSITION_Y);
        String z = jsonObject.getJSONObject(PARAMS).getJSONObject(POSITION).getString(POSITION_Z);

        return Arrays.asList(x, y, z);
    }

    public List<String> getRotation() {
        String x = jsonObject.getJSONObject(PARAMS).getJSONObject(ROTATION).getString(ROTATION_X);
        String y = jsonObject.getJSONObject(PARAMS).getJSONObject(ROTATION).getString(ROTATION_Y);
        String z = jsonObject.getJSONObject(PARAMS).getJSONObject(ROTATION).getString(ROTATION_Z);

        return Arrays.asList(x, y, z);
    }

    public List<String> getDeflection() {
        String x = jsonObject.getJSONObject(PARAMS).getJSONObject(DEFLECTION).getString(DEFLECTION_X);
        String y = jsonObject.getJSONObject(PARAMS).getJSONObject(DEFLECTION).getString(DEFLECTION_Y);
        String z = jsonObject.getJSONObject(PARAMS).getJSONObject(DEFLECTION).getString(DEFLECTION_Z);

        return Arrays.asList(x, y, z);
    }

    public List<String> getOrientation() {
        String x = jsonObject.getJSONObject(PARAMS).getJSONObject(ORIENTATION).getString(PITCH);
        String y = jsonObject.getJSONObject(PARAMS).getJSONObject(ORIENTATION).getString(YAW);
        String z = jsonObject.getJSONObject(PARAMS).getJSONObject(ORIENTATION).getString(ROLL);

        return Arrays.asList(x, y, z);
    }

    public String getJSONVersion() {
        return jsonObject.getString(VERSION_JSON);
    }

    public String getMethod() {
        return jsonObject.getString(METHOD);
    }

    public String getModule() {
        return jsonObject.getString(MODULE);
    }

    public String getBundle() {
        return jsonObject.getJSONObject(PARAMS).getString(BUNDLE);
    }

    public String getTimestamp() {
        return jsonObject.getJSONObject(PARAMS).getString(TIMESTAMP);
    }

    public String getStatus() {
        return jsonObject.getJSONObject(PARAMS).getString(STATUS);
    }

    public String getJSONString() {
        return jsonObject.toString();
    }
}
