`zookeeper-server-start.bat config\zookeeper.properties`

`kafka-server-start.bat config\server.properties`

`kafka-topics --bootstrap-server 127.0.0.1:9092 --topic orders --create --partitions 3 --replication-factor 1`

`kafka-topics --bootstrap-server 127.0.0.1:9092 --topic notifications --create --partitions 3 --replication-factor 1`


Example of data for new order creation:
```
[
    {
        "menuItem": "Margerita",
        "quantity": 2
    },
    {
        "menuItem": "BBQ",
        "quantity": 3
    }
]
```