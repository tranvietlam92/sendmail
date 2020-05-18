# sendmail
Send mail using spring boot
I. Cấu hình jobmail ngoài liferay thêm những không tin sau:
    1. Add line to "portal-setup-winzard.properties"  :   opencps.notify.job.mail=true
    2. Thư mục file config.properties : /opt/SendMail/

II. Start mail với spring boot
    1. Start bang cách tạo service với systemd
        - Tạo file : SendMail.service  in path : /etc/systemd/system/
        -  Nội dung file:
            [Unit]
            Description=SendMail
            After=syslog.target

            [Service]
            #Type=forking
            User=tomcat
            Group=tomcat
            ExecStart=/opt/SendMail.jar
            SuccessExitStatus=143

            [Install]
            WantedBy=multi-user.target

        - Chú thích : 
                + Description : Tên app
                + User : user start app
                    VD: tomcat =>  Create user : useradd tomcat
                        Change permission : chown -R tomcat:tomcat : /opt/SendMail.jar 
                + ExecStart : Đường dẫn chứa thư mục jar
        
        - Gõ lệnh: systemctl enable SendMail
                    systemctl start/stop/restart SendMail
        
        - Xem log: tail -f /var/log/message
