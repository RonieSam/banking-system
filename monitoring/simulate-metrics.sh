#!/bin/bash

while true
do
  echo "banking-system.cpu.usage $((RANDOM % 100)) $(date +%s)" | nc localhost 2003
  echo "banking-system.memory.usage $((1000 + RANDOM % 500)) $(date +%s)" | nc localhost 2003
  echo "banking-system.response.time $((100 + RANDOM % 300)) $(date +%s)" | nc localhost 2003
  sleep 5
done
