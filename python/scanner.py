import json
import socket
from concurrent.futures import ThreadPoolExecutor
from scapy.all import ARP, Ether, srp

COMMON_PORTS = [22, 80, 443, 3389]
DEFAULT_TIMEOUT = 0.5
MAX_THREADS = 30


def resolve_hostname(ip):
    try:
        return socket.gethostbyaddr(ip)[0]
    except socket.herror:
        return "unknown"


def scan_port(ip, port):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.settimeout(DEFAULT_TIMEOUT)
        try:
            sock.connect((ip, port))
            return port
        except:
            return None


def scan_ports(ip, ports=None):
    ports = ports or COMMON_PORTS
    open_ports = []

    with ThreadPoolExecutor(max_workers=MAX_THREADS) as executor:
        results = executor.map(lambda p: scan_port(ip, p), ports)

    return [port for port in results if port is not None]


def scan_network(ip_range="192.168.15.0/24"):
    arp_request = ARP(pdst=ip_range)
    broadcast = Ether(dst="ff:ff:ff:ff:ff:ff")
    packet = broadcast / arp_request

    answered, _ = srp(packet, timeout=1, verbose=0)
    devices = []

    for _, response in answered:
        ip = response.psrc
        mac = response.hwsrc
        hostname = resolve_hostname(ip)
        ports = scan_ports(ip)
        devices.append({
            "ip": ip,
            "mac": mac,
            "hostname": hostname,
            "ports": ports
        })

    return devices


def main():
    devices = scan_network()
    output = {
        "status": "ok",
        "device_count": len(devices),
        "devices": devices
    }
    print(json.dumps(output, indent=2))


if __name__ == "__main__":
    main()
