
# Kubernetes Deployment for Spring Boot + PostgreSQL Application

This repository contains Kubernetes manifests structured according to industry best practices for deploying a Java Spring Boot application integrated with a PostgreSQL database.

---

## Folder Structure

```
nagp-k8s-devops/
├── kubernetes/config/
│   ├── db-config-and-secret.yaml         # ConfigMap and Secret for database configuration
│   ├── db-secret.yaml                    # (Optional) Secret for database credentials
│
├── kubernetes/database/
│   ├── postgres-deployment.yaml          # Deployment manifest for PostgreSQL
│   ├── postgres-pvc.yaml                 # PersistentVolumeClaim for PostgreSQL data
│   ├── postgres-service.yaml             # ClusterIP Service for PostgreSQL
│   ├── postgres-initdb-configmap.yaml    # ConfigMap containing init.sql to initialize the DB
│
├── kubernetes/app/
│   ├── myservice-deployment.yaml         # Deployment manifest for Spring Boot microservice
│   ├── myservice-service.yaml            # ClusterIP Service for the microservice
│   ├── myservice-ingress.yaml            # Ingress for external HTTP access
│
├── src/                                  # Java Spring Boot source code
├── pom.xml                               # Maven project dependencies
├── Dockerfile                            # Dockerfile to build the Spring Boot app container
└── README.md                             # Project documentation
```

---

## Deployment Steps

1. **Set up your Kubernetes cluster** (GKE, Minikube, etc.).

2. **Build and push your Docker image**:
   ```bash
   docker build -t <dockerhub-username>/nagp-app:latest .
   docker push <dockerhub-username>/nagp-app:latest
   ```

3. **Login to Google Cloud, create the Kubernetes cluster, and upload the `kubernetes` folder.**

4. **Apply configuration manifests**:
   ```bash
   cd kubernetes/config
   kubectl apply -f db-config-and-secret.yaml
   kubectl apply -f db-secret.yaml   # If applicable
   ```

5. **Deploy the PostgreSQL database**:
   ```bash
   cd ../database
   kubectl apply -f postgres-pvc.yaml
   kubectl apply -f postgres-initdb-configmap.yaml
   kubectl apply -f postgres-deployment.yaml
   kubectl apply -f postgres-service.yaml
   ```

6. **Verify database pods and services**:
   ```bash
   kubectl get pods -n public
   kubectl get svc postgres -n public
   kubectl get pvc -n public
   kubectl describe pod <pod-name> -n public
   ```

7. **Deploy the Spring Boot application**:
   ```bash
   cd ../app
   kubectl apply -f myservice-deployment.yaml
   kubectl apply -f myservice-service.yaml
   kubectl apply -f myservice-ingress.yaml
   ```

8. **Verify application pods and ingress**:
   ```bash
   kubectl get pods -l app=myservice -n public
   kubectl logs <pod-name> -n public
   kubectl get ingress -n public
   kubectl describe ingress myservice-ingress -n public
   curl http://<ingress-external-ip>
   ```

---

## Notes

- Replace `<dockerhub-username>` with your actual Docker Hub username.
- Ensure an Ingress Controller (e.g., NGINX) is installed in the cluster before applying the ingress manifest.
- Namespace `public` is used throughout; you may modify this as per your environment.
- Use Kubernetes Secrets securely in production environments and avoid committing sensitive data to version control.
