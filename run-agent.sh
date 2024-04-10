docker run -d --cgroupns host --pid host --name dd-agent \
    -v /var/run/docker.sock:/var/run/docker.sock:ro \
    -v /proc/:/host/proc/:ro \
    -v /sys/fs/cgroup/:/host/sys/fs/cgroup:ro \
    -e DD_SITE=datadoghq.eu \
    -e DD_ENV=otel-demo \
    -e DD_API_KEY \
    -e DD_OTLP_CONFIG_RECEIVER_PROTOCOLS_GRPC_ENDPOINT=0.0.0.0:4317 \
    -e DD_OTLP_CONFIG_RECEIVER_PROTOCOLS_HTTP_ENDPOINT=0.0.0.0:4318 \
    -p 4317:4317 \
    -p 4318:4318 \
    gcr.io/datadoghq/agent:7
