# aws-codedeploy-sample-tomcat
A sample Tomcat application integrated with CodeDeploy. 


sudo yum update -y
sudo yum install -y ruby wget
cd /home/ec2-user
wget https://aws-codedeploy-ap-south-1.s3.ap-south-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
sudo service codedeploy-agent start

Use ROLE : CodeDeploy-ec2-role

sudo service codedeploy-agent restart
