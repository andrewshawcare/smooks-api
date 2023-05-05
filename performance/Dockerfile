# syntax=docker/dockerfile:1
# Build
FROM golang:1.20-alpine AS builder

WORKDIR $GOPATH/src/go.k6.io/k6
RUN apk --no-cache add git=~2
RUN git clone https://github.com/grafana/k6.git .
RUN go build -a -trimpath -ldflags "-s -w -X go.k6.io/k6/lib/consts.VersionDetails=$(date -u +"%FT%T%z")/$(git describe --tags --always --long --dirty)" -o /usr/bin/k6 .

# Runtime stage
FROM alpine:3.17
RUN apk add --no-cache ca-certificates=~20220614 && \
    adduser -D -u 12345 -g 12345 k6
COPY --from=builder /usr/bin/k6 /usr/bin/k6

USER 12345
WORKDIR /home/k6
ENTRYPOINT ["k6"]