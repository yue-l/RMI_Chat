package chatrmi.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author caroline & yue
 */
public class VectorTime {

    private Map<String, Integer> timestamps;
    private final String localAddress;

    public VectorTime(String address) {
        timestamps = new HashMap();
        this.localAddress = address;
        timestamps.put(localAddress, 0);
    }

    public synchronized int increateTimestamp() {

        int current = timestamps.get(this.localAddress);
        current++;
        timestamps.put(this.localAddress, current);

        return current;
    }

    public synchronized void updateTimestamp(String address, int time) {
        this.timestamps.put(address, time);
    }

    public synchronized int getTimestamp(String address) {
        int time = 0;
        if (timestamps.containsKey(address)) {
            time = timestamps.get(address);
        }
        return time;
    }

    public synchronized Map<String, Integer> getVectorTimestamps() {
        Map<String, Integer> map = new HashMap();
        for (String str : timestamps.keySet()) {
            map.put(str, timestamps.get(str));
        }
        return map;
    }
}
