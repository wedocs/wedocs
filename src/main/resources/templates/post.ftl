<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Wedocs</title>
    <#include "css.ftl">
</head>
<body>
<#include "header.ftl">
<section class="documentation">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <aside>
                    <div id="sidebar" class="nav-collapse">
                        <ul class="sidebar-menu">
                            <li class="sub-menu active">
                                <a href="../overview/">
                                    <i class="fa fa-hand-o-right fas"></i>
                                    <span>Overview</span>
                                </a>
                                <ul class="sub">
                                    <li class="active"><a href="../overview/"> Project</a></li>
                                    <li><a href="../overview/architecture/"> Architecture</a></li>
                                    <li><a href="../overview/features/"> Features</a></li>
                                </ul>
                            </li>

                            <li class="sub-menu">
                                <a href="../guides/">
                                    <i class="fa fa-book fas"></i>
                                    <span>Guides</span>
                                </a>
                                <ul class="sub">
                                    <li><a href="../guides/debian-ubuntu/"> Quickstart Debian / Ubuntu</a></li>
                                    <li><a href="../guides/docker-compose/"> Quickstart Docker-Compose</a></li>
                                    <li><a href="../guides/google-cloud-platform/"> Quickstart Google Cloud Platform</a>
                                    </li>
                                    <li><a href="../guides/microsoft-azure/"> Quickstart Microsoft Azure</a></li>
                                    <li><a href="../guides/ansible-vagrant/"> Ansible and Vagrant</a></li>
                                    <li><a href="../guides/raspberry-pi/"> Raspberry Pi</a></li>
                                    <li><a href="../guides/mqtt-authentication/"> MQTT authentication</a></li>
                                    <li><a href="../guides/first-gateway-device/"> First gateway and device</a></li>
                                    <li><a href="../guides/thingsboard/"> ThingsBoard getting started</a></li>
                                    <li><a href="../guides/troubleshooting/"> Troubleshooting</a></li>
                                </ul>
                            </li>

                            <li class="sub-menu">
                                <a href="../install/">
                                    <i class="fa fa-download fas"></i>
                                    <span>Install</span>
                                </a>
                                <ul class="sub">
                                    <li><a href="../install/requirements/"> Requirements</a></li>
                                    <li><a href="../install/install/"> General Installation</a></li>
                                    <li><a href="../install/docker/"> Docker install</a></li>
                                    <li><a href="../install/configuration/"> Configuration</a></li>
                                </ul>
                            </li>

                            <li class="sub-menu">
                                <a href="../community/">
                                    <i class="fa fa-github fas"></i>
                                    <span>Community &amp; support</span>
                                </a>
                                <ul class="sub">
                                    <li><a href="../community/support/"> Support</a></li>
                                    <li><a href="../community/contribute/"> Contribute</a></li>
                                    <li><a href="../community/source/"> Source</a></li>
                                    <li><a href="../community/links/"> Links</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </aside>

            </div>
            <div class="col-md-9 content">

            </div>
        </div>
    </div>
</section>
<#include "footer.ftl">
</body>
    <#include "javascript.ftl">
</html>




