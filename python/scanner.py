import scapy.all as scapy
import json
import socket

def scan(ip_range="192.168.15.0/24"):
    devices = []
    arp_request = scapy.ARP(pdst=ip_range)
    broadcast = scapy.Ether(dst="ff:ff:ff:ff:ff:ff")
    arp_request_broadcast = broadcast / arp_request
    answered = scapy.srp(arp_request_broadcast, timeout=1, verbose=0)[0]
    
    for element in answered:
        ip = element[1].psrc
        mac = element[1].hwsrc
        hostname = resolve_hostname(ip)
        devices.append({
            "ip": ip,
            "mac": mac,
            "hostname": hostname,
            "ports": scan_ports(ip)
        })
    return devices

def scan_ports(ip):
    common_ports = [22, 80, 443, 3389]
    open_ports = []
    for port in common_ports:
        sock = socket.socket()
        sock.settimeout(0.5)
        try:
            sock.connect((ip, port))
            open_ports.append(port)
        except:
            pass
        finally:
            sock.close()
    return open_ports

def resolve_hostname(ip):
    try:
        return socket.gethostbyaddr(ip)[0]
    except:
        return "unknown"

if __name__ == "__main__":
    result = scan()
    print(json.dumps({"status": "ok", "devices": result}))