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



                                    <li><a href="../guides/google-cloud-platform/"> Quickstart Google Cloud Platform</a></li>



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




                <h1 id="the-lora-server-project">The LoRa Server project</h1>

                <h2 id="what-is-lora">What is LoRa?</h2>

                <p>LoRaWAN is a long range, low power wireless protocol that is intended for use
                    in building IoT networks.  IoT devices (“nodes”) send small data packets to
                    any number of “gateways” that may be in the several-kilometer range of a node
                    via the LoRaWAN wireless protocol. The gateways then use more traditional
                    communications such as wired Internet connections to forward the messages
                    to a network-server which validates the packets and forwards the application
                    payload to an application-server.</p>

                <p>The nature of the LoRa network potentially allows IoT devices to run for years
                    on small batteries, occasionally sending out small packets of data, waiting for
                    a short time for response messages, and then closing the connection until more
                    data needs to be sent. Devices can also be set up so that they are always
                    listening for messages from their applications, though this obviously requires
                    more power and may be more appropriate for devices that are, say, plugged in
                    to a wall socket.</p>

                <p>Of course there is much more to LoRaWAN than is described here. The LoRaWAN
                    protocol is defined and managed by the <a href="https://www.lora-alliance.org/">LoRa Alliance</a>.
                    There is a great deal of information available there.</p>

                <h2 id="about-the-lora-server-project">About the Lora Server Project</h2>

                <p>The LoRa Server project is an open-source set of applications that fill the
                    gap between the gateways receiving messages from the nodes to just before the
                    applications receiving the data. It provides mechanisms for managing the
                    gateways on the LoRa network, the applications supported, and the devices
                    associated with the applications.</p>

                <p>The project is designed so that it may be used in a very flexible manner.
                    For example the <a href="../lora-app-server/">LoRa App Server</a> component implements
                    the application-server component and offers a Web UI for users to access and
                    modify their gateways, applications and nodes. The system can also be accessed
                    via programmatic interfaces implemented in <a href="http://www.grpc.io/">gRPC</a> and
                    JSON REST APIs. Further, the APIs are designed such that the subsystems may
                    be replaced by other software implementing the same interfaces.</p>

                <p>For a more technical understanding of the parts of the Lora Server software
                    system and how they work together, please refer to the
                    <a href="../overview/architecture/">architecture</a> page.</p>



                <div class="row content-list">

                    <div class="col-md-4 item">
                        <h5><a href="../overview/architecture/">Architecture</a></h5>
                        <p>Overview of the LoRa Server architecture and how components are connected.</p>
                    </div>

                    <div class="col-md-4 item">
                        <h5><a href="../overview/features/">Features</a></h5>
                        <p>Overview of the features provided by the LoRa Server project.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<#include "footer.ftl">
</body>
    <#include "javascript.ftl">
</html>




