package com.hit.api.utils;

import com.hit.common.utils.StringUtils;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@UtilityClass
public class IPAddressUtils {

    private final String LOCALHOST_IPV4 = "127.0.0.1";

    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    private final String UNKNOWN = "unknown";

    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmptyOrBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isEmptyOrBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmptyOrBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("Get localhost ipaddress error", e);
                }
            }
        }

        if (!StringUtils.isNotEmptyOrBlank(ipAddress) && ipAddress.length() > 15 && ipAddress.contains(",")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        return ipAddress;
    }

    @SneakyThrows
    public static boolean checkIPInRange(String inputIP, String rangeStartIP, String rangeEndIP) {
        IPAddress startIPAddress = new IPAddressString(rangeStartIP).getAddress();
        IPAddress endIPAddress = new IPAddressString(rangeEndIP).getAddress();
        IPAddressSeqRange ipRange = startIPAddress.toSequentialRange(endIPAddress);
        IPAddress inputIPAddress = new IPAddressString(inputIP).toAddress();
        return ipRange.contains(inputIPAddress);
    }
}
