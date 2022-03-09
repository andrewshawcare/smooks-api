import logging
import os
import time
from kafka import KafkaConsumer
from kafka.errors import NoBrokersAvailable

logging.basicConfig(level=os.environ.get('LOGLEVEL', 'INFO'))
logger = logging.getLogger(__name__)

bootstrap_server = os.environ.get('KAFKA_BOOTSTRAP_SERVER', 'kafka:9092')

sleep_duration = 1

while True:
    try:
        kafkaConsumer = KafkaConsumer('events', bootstrap_servers=[bootstrap_server])
        while True:
            for message in kafkaConsumer:
                logger.info(message)
    except NoBrokersAvailable as noBrokersAvailable:
        logger.info(f'No brokers available, sleeping for {sleep_duration}s')
        time.sleep(sleep_duration)
