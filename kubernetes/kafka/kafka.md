
### zookeeper-service.yml
The kafka-zookeeper service resolves the domain name kafka-zookeeper to an 
internal ClusterIP. The automatically assigned ClusterIP uses Kubernetes 
internal proxy to load balance calls to any Pods found from the configured selector, 
in this case, `app: kafka-zookeeper`.


### zookeeper-service-headless.yml
A Kubernetes Headless Service does not resolve to a single IP; 
instead, Headless Services returns the IP addresses of any Pods found 
by their selector, in this case, Pods labeled `app: kafka-zookeeper`.

Once Pods labeled `app: kafka-zookeeper` are running, this Headless Service 
returns the results of an in-cluster DNS lookup. At this point, no Pod IPs 
can be returned until the Pods are configured in the StatefulSet further down.


### zookeeper-statefulset.yml
Kubernetes StatefulSets offer stable and unique network identifiers, 
persistent storage, ordered deployments, scaling, deletion, termination, 
and automated rolling updates.


### zookeeper-disruptionbudget.yml
A PDB specifies the number of replicas that an application can tolerate having, 
relative to how many it is intended to have. For example, a Deployment which has 
a .spec.replicas: 5 is supposed to have 5 pods at any given time. 
If its PDB allows for there to be 4 at a time, then the Eviction API will allow 
voluntary disruption of one, but not two pods, at a time.


### kafka-service.yml
The following Service provides a persistent internal Cluster IP address 
that proxies and load balance requests to Kafka Pods found with the label 
`app: kafka` and exposing the port `9092`.


### kafka-service-headless.yml
Headless Service provides a list of Pods and their internal IPs found with the 
label `app: kafka` and exposing the port `9092`. The previously created 
Service: `kafka` always returns a persistent IP assigned at the creation 
time of the Service. The following kafka-headless services return the domain 
names and IP address of individual Pods and are liable to change as Pods are added, 
removed or updated.


### kafka-statefulset.yml
The following StatefulSet deploys Pods running the `confluentinc/cp-kafka:4.1.2-2` 
Docker image from Confluent.
Each pod is assigned 1Gi of storage using the rook-block storage class. 
See Rook.io for more information on file, block, and object storage services 
for cloud-native environments.


### pod-test.yml
The Confluent Docker image `confluentinc/cp-kafka:4.1.2-2` used for the test 
Pod is the same as our nodes from the StatefulSet and contain useful command 
in the `/usr/bin/` folder.

- List Topics
```bash
kubectl -n the-project exec kafka-test-client -- \
    /usr/bin/kafka-topics --zookeeper kafka-zookeeper:2181 --list
```
- Create Topic
```bash
kubectl -n the-project exec kafka-test-client -- \
    /usr/bin/kafka-topics --zookeeper kafka-zookeeper:2181 \
    --topic test --create --partitions 1 --replication-factor 1
```
- Listen on a Topic
```bash
kubectl -n the-project exec -ti kafka-test-client -- \
    /usr/bin/kafka-console-consumer --bootstrap-server kafka:9092 \
    --topic test --from-beginning
```
