import socket
import time

GRAPHITE_SERVER = 'localhost'
GRAPHITE_PORT = 2003

while True:
    timestamp = int(time.time())
    # Send some dummy metrics
    metrics = {
        'banking-system.cpu.usage': 60,
        'banking-system.memory.usage': 45,
        'banking-system.response.time': 100,
    }

    sock = socket.socket()
    sock.connect((GRAPHITE_SERVER, GRAPHITE_PORT))

    for key, value in metrics.items():
        message = f"{key} {value} {timestamp}\n"
        sock.sendall(message.encode())

    sock.close()
    time.sleep(5)
