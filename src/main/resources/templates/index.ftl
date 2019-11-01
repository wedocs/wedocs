<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Wedocs</title>
    <#include "css.ftl">
</head>
<body>
<#include "header.ftl">
<div class="container">
    <section class="jumbotron">
        <div class="container text-center">
            <h1>LoRa Server, open-source LoRaWAN network-server</h1>
            <h3>Sponsored by
                <a href="http://www.cablelabs.com">
                    <strong>Cable</strong>Labs</a>
                <sup>&reg;</sup>
            </h3>
        </div>
    </section>

    <hr/>

    <section class="intro text-center py-3">
        <p class="content">
            The LoRa Server project provides open-source components for building LoRaWAN networks. Together they form a
            ready-to-use
            solution, including an user-friendly web-interface and gRPC and REST APIs. Components can also be swapped
            out for
            customization or when integrating LoRa Server into existing infrastructures. All components are licensed
            under the
            MIT license and can be used for commercial purposes.
        </p>
        <p><a href="/overview/" class="btn btn-outline-primary">Get started</a></p>
    </section>

    <hr/>

    <section class="screenshots py-3">
        <div class="row">

            <div id="las_screenshots" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#las_screenshots" data-slide-to="0" class="active"></li>
                    <li data-target="#las_screenshots" data-slide-to="1"></li>
                    <li data-target="#las_screenshots" data-slide-to="2"></li>
                    <li data-target="#las_screenshots" data-slide-to="3"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="d-block w-100" src="/img/screenshots/web_applications.png" alt="Applications"/>
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="/img/screenshots/web_nodes.png" alt="Devices"/>
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="/img/screenshots/web_node_details.png" alt="Live device data"/>
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="/img/screenshots/swagger.png" alt="Swagger API interface"/>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#las_screenshots" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#las_screenshots" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

        </div>
    </section>

    <hr/>

    <section class="features py-3">
        <h2 class="text-center">Feature highlights</h2>
        <div class="row py-3">
            <div class="col-sm-4">
                <h4>Class-A support</h4>
                <p class="content">
                    End-devices of Class A allow for bi-directional communications whereby each end-device‘s uplink
                    transmission is followed
                    by two short downlink receive windows. The transmission slot scheduled by the end-device is based on
                    its own
                    communication needs with a small variation based on a random time basis (ALOHA-type of protocol).
                </p>
            </div>
            <div class="col-sm-4">
                <h4>Class-B support</h4>
                <p class="content">
                    End-devices of Class B allow for more receive slots. In addition to the Class A random receive
                    windows, Class B devices open
                    extra receive windows at scheduled times. In order for the End-device to open it receive window at
                    the scheduled
                    time it receives a time synchronized Beacon from the gateway.
                </p>
            </div>
            <div class="col-sm-4">
                <h4>Class-C support</h4>
                <p class="content">
                    End-devices of Class C have nearly continuously open receive windows, only closed when transmitting.
                    Class C end-device will
                    use more power to operate than Class A or Class B but they offer the lowest latency for server to
                    end-device
                    communication.
                </p>
            </div>
        </div>

        <div class="row py-3">
            <div class="col-sm-4">
                <h4>Adaptive data-rate</h4>
                <p class="content">
                    When the end-device has ADR enabled, LoRa Server will ensure that the device will operate using the
                    most ideal data-rate
                    and tx-power. this will not only save energy at the device-side, but will also optimize the usage of
                    the radio
                    spectrum, lowering the risk of collisions.
                </p>
            </div>

            <div class="col-sm-4">
                <h4>Live frame-logging</h4>
                <p class="content">
                    LoRa (App) Server provides live frame-logging per gateway and device. It will display all the RX /
                    TX meta-data, together
                    with the raw LoRaWAN PHYPayload in a readable format. It is like Wireshark for LoRaWAN!
                </p>
            </div>

            <div class="col-sm-4">
                <h4>Channel (re)configuration</h4>
                <p class="content">
                    Whether you want to use a sub-set of the LoRaWAN defined channels (e.g. for the US band) or want to
                    configure additional
                    channels (e.g. for the EU band), LoRa Server will make sure the device stays always in sync with the
                    network
                    configured channels (using the CFList field and / or mac-commands).
                </p>
            </div>
        </div>

        <div class="row py-3">
            <div class="col-sm-4">
                <h4>Multi-tenant</h4>
                <p class="content">
                    LoRa App Server supports hosting multiple organizations to which (administrator) users can be
                    assigned. By using PBKDF2 password
                    hashing, LoRa App Server facilitates the integration with MQTT broker authentication and
                    authorization so that organizations
                    will only be able to see their own device payloads.
                </p>
            </div>

            <div class="col-sm-4">
                <h4>APIs and integration</h4>
                <p class="content">
                    All components provide gRPC and / or REST APIs to enable the integration with external services. By
                    default all application
                    data is published over MQTT channels. Per application HTTP endpoints can be setup for application
                    data and events.
                </p>
            </div>

            <div class="col-sm-4">
                <h4>LoRaWAN 1.0 and 1.1 compatible</h4>
                <p class="content">
                    LoRa Server supports both LoRaWAN 1.0 and LoRaWAN 1.1 devices, including all Regional Parameter
                    Specification
                    revisions and bands.
                </p>
            </div>
        </div>

        <div class="row py-3">
            <div class="col-sm-12">
                <h4 class="text-center">And there is much more...</h4>
                <p class="content text-center">
                    Please refer to the <a href="/overview/">LoRa Server project documentation</a> to learn more about
                    all the features
                    and configuration options!
                </p>
            </div>
        </div>
    </section>

    <hr/>

    <section class="lpwanserver py-3">
        <h2 class="text-center">LPWAN Server by CableLabs</h2>
        <p class="content text-center">
            For unifying the management of multiple LPWAN technologies or LoRaWAN networks, please take a look at the
            open-source <a href="https://lpwanserver.com">LPWAN Server project</a>.
        </p>
    </section>

    <hr/>

    <section class="support py-3">
        <h2 class="text-center">Support</h2>
        <div class="row py-3">
            <div class="col-sm-4">
                <h4>Reporting bugs</h4>
                <p class="content">
                    Please report a bug by creating an issue at the related GitHub repository.
                    GitHub links can be found at the documentation page of each component.
                </p>
            </div>
            <div class="col-sm-4">
                <h4>Community support</h4>
                <p class="content">
                    For questions and community support, please refer to <a href="https://forum.loraserver.io">forum.loraserver.io</a>.
                </p>
            </div>
            <div class="col-sm-4">
                <h4>Commercial support</h4>
                <p class="content">
                    For commercial support contact <a href="http://www.brocaar.com">Orne Brocaar</a>, the author of the
                    LoRa Server project.
                </p>
            </div>
        </div>
    </section>

    <hr/>

    <section class="sponsors py-3">
        <h2 class="text-center">Sponsors</h2>
        <div class="row py-3">
            <div class="col-sm-4">
                <a href="http://www.cablelabs.com/">
                    <img src="img/sponsors/cablelabs.png"/>
                </a>
            </div>
            <div class="col-sm-4">
                <a href="http://www.sidnfonds.nl/">
                    <img src="img/sponsors/sidn_fonds.png"/>
                </a>
            </div>
            <div class="col-sm-4">
                <a href="http://www.acklio.com/">
                    <img src="img/sponsors/acklio.png"/>
                </a>
            </div>
        </div>
    </section>
</div>
<#include "footer.ftl">
</body>
    <#include "javascript.ftl">
</html>




