#!/bin/bash
# 容器名称
container_name=$1
# 镜像名称
image_name=$1
# 镜像tag
image_tag=$2

# 判断容器是否存在
if docker ps -a | grep $container_name | awk '{print $1}'; then
  echo "容器 $container_name 存在"
  if docker ps | grep $container_name | awk '{print $1}';then
     echo "关闭正在运行的容器 $container_name"
     docker stop `docker ps | grep $container_name | awk '{print $1}'`
  else
    echo "容器 $container_name 都已关闭"
  fi
  # 删除容器
  echo "删除容器 $container_name"
  docker rm `docker ps -a | grep $container_name | awk '{print $1}'`
else
  echo "容器 $container_name 不存在"
fi

# 启动容器
echo "启动容器 $container_name"
if [ $container_name = "zzyl-admin" ]; then
    docker run -d --restart=always --name $container_name -v /usr/local/zzyl-admin/logs:/home/ruoyi/logs -p 9000:9000 $image_name:$image_tag
fi