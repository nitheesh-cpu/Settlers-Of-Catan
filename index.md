---
layout: home
title: "Settlers of Catan Project"
heading: "Settlers of Catan Recreation"
subheading: "A JavaFX recreation of the classic strategy board game where resource management meets territorial expansion"
---

<div class="space-y-12">
    <!-- Main Featured Demo GIF -->
    <div class="flex justify-center">
        <img class="object-cover object-top w-full max-w-4xl h-auto rounded-lg shadow-lg" 
             src="{{ site.baseurl }}/assets/img/settlers-catan-demo.gif" 
             alt="Settlers of Catan Game Demo - Strategic Board Game">
    </div>

    <div class="space-y-6">
        <div class="space-y-4">
            <h3 class="text-3xl font-bold leading-tight text-gray-900 sm:text-4xl dark:text-white">
                Settlers of Catan Recreation
            </h3>

            <p class="text-base font-normal text-gray-500 sm:text-lg dark:text-gray-400">
                This project is a recreation of the board game <em>Settlers of Catan</em> in Java. Settlers of Catan is a 3-4 player competitive multiplayer board game in which players must gather resources and build roads, settlements, and cities in attempts to be the first to 10 victory points.
            </p>
            <p class="text-base font-normal text-gray-500 sm:text-lg dark:text-gray-400">
                Built with JavaFX and featuring modern <a href="https://github.com/kordamp/bootstrapfx" target="_blank" class="text-primary-600 hover:text-primary-700 dark:text-primary-500">BootstrapFX</a> styling, this recreation captures the strategic depth and competitive spirit of the original game. As noted by the <a href="https://github.com/ramzidaher/SettlersOfCatan">JavaFX Settlers of Catan implementations</a>, this version provides "a platform for friends and families to connect, strategize, and have fun" with faithful recreation of all game mechanics.
            </p>
        </div>

        <!-- Technology Tags -->
        <div class="flex items-center gap-2.5 flex-wrap">
            <div class="p-1 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800">
                <span class="inline-flex items-center px-3 py-1 text-xs font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300">
                    Java
                </span>
            </div>
            <div class="p-1 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800">
                <span class="inline-flex items-center px-3 py-1 text-xs font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300">
                    JavaFX
                </span>
            </div>
            <div class="p-1 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800">
                <span class="inline-flex items-center px-3 py-1 text-xs font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300">
                    BootstrapFX
                </span>
            </div>
            <div class="p-1 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800">
                <span class="inline-flex items-center px-3 py-1 text-xs font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300">
                    Maven
                </span>
            </div>
            <div class="p-1 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800">
                <span class="inline-flex items-center px-3 py-1 text-xs font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300">
                    Competitive Strategy
                </span>
            </div>
            <div class="p-1 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-800">
                <span class="inline-flex items-center px-3 py-1 text-xs font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300">
                    Resource Management
                </span>
            </div>
        </div>

        <!-- Screenshot Gallery -->
        <div class="mt-8">
            <h4 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">Game Features</h4>

                        <!-- Main Screenshots Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                <div class="bg-white dark:bg-gray-900 rounded-lg border border-gray-200 dark:border-gray-700 overflow-hidden">
                    <img class="w-full h-48 object-cover"
                         src="{{ site.baseurl }}/assets/img/build.png"
                         alt="Building Interface - Roads, Settlements, and Cities">
                    <div class="p-4">
                        <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Strategic Building</h5>
                        <p class="text-sm text-gray-600 dark:text-gray-400">Build roads, settlements, and cities to expand your territory and gain victory points.</p>
                    </div>
                </div>

                <div class="bg-white dark:bg-gray-900 rounded-lg border border-gray-200 dark:border-gray-700 overflow-hidden">
                    <img class="w-full h-48 object-cover"
                         src="{{ site.baseurl }}/assets/img/cards.png"
                         alt="Development Cards and Trading System">
                    <div class="p-4">
                        <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Cards & Trading</h5>
                        <p class="text-sm text-gray-600 dark:text-gray-400">Collect development cards and engage in strategic trading with other players.</p>
                    </div>
                </div>

                <div class="bg-white dark:bg-gray-900 rounded-lg border border-gray-200 dark:border-gray-700 overflow-hidden">
                    <img class="w-full h-48 object-cover"
                         src="{{ site.baseurl }}/assets/img/help.png"
                         alt="Game Rules and Help System">
                    <div class="p-4">
                        <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Comprehensive Help</h5>
                        <p class="text-sm text-gray-600 dark:text-gray-400">Built-in help system with complete game rules and strategy tips.</p>
                    </div>
                </div>

                <div class="bg-white dark:bg-gray-900 rounded-lg border border-gray-200 dark:border-gray-700 overflow-hidden">
                    <img class="w-full h-48 object-cover"
                         src="{{ site.baseurl }}/assets/img/darkmode.png"
                         alt="Dark Mode Interface - Modern UI Theme">
                    <div class="p-4">
                        <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Dark Mode Theme</h5>
                        <p class="text-sm text-gray-600 dark:text-gray-400">Modern dark theme interface for enhanced gaming experience during extended play sessions.</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- How to Play Section -->
        <div class="mt-8">
            <h4 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">How to Play</h4>

            <!-- Game Overview -->
            <div class="bg-gray-50 dark:bg-gray-900 rounded-lg p-6 mb-8">
                <h5 class="text-xl font-semibold text-gray-900 dark:text-white mb-4">The Island of Catan Awaits</h5>
                <p class="text-base text-gray-600 dark:text-gray-400 mb-4">
                    Players assume roles as settlers, each attempting to build and develop their individual holdings while trading and acquiring resources. The game is set up randomly, ensuring a unique experience every time. Players are given choices between <strong>building</strong>, <strong>using cards</strong>, and <strong>trading</strong> to achieve victory.
                </p>
                <div class="flex justify-center mb-4">
                    <img class="w-full max-w-2xl h-auto rounded border border-gray-200 dark:border-gray-700"
                         src="{{ site.baseurl }}/assets/img/settlers-catan-demo.gif" alt="Settlers of Catan Gameplay Demo">
                </div>
                <p class="text-sm text-gray-600 dark:text-gray-400">
                    Strategic planning and resource management are essential - the first player to reach 10 victory points wins! Check out the <a href="https://www.catan.com/understand-catan/game-rules" target="_blank" class="text-primary-600 hover:text-primary-700 dark:text-primary-500">official rules</a> for complete gameplay details.
                </p>
            </div>

            <!-- Game Mechanics Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <!-- Resource Management -->
                <div class="bg-gray-50 dark:bg-gray-900 rounded-lg p-6">
                    <h5 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Resource Management</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400 mb-4">Collect and manage five resource types:</p>
                    <ul class="text-sm text-gray-600 dark:text-gray-400 space-y-2">
                        <li class="flex items-center"><span class="w-2 h-2 bg-yellow-500 rounded-full mr-2"></span>Wheat (Agriculture)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-green-600 rounded-full mr-2"></span>Wood (Lumber)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-red-600 rounded-full mr-2"></span>Brick (Clay)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-gray-600 rounded-full mr-2"></span>Stone (Ore)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-gray-400 rounded-full mr-2"></span>Sheep (Wool)</li>
                    </ul>
                </div>

                <!-- Building Options -->
                <div class="bg-gray-50 dark:bg-gray-900 rounded-lg p-6">
                    <h5 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Building & Development</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400 mb-4">Expand your empire through construction:</p>
                    <ul class="text-sm text-gray-600 dark:text-gray-400 space-y-2">
                        <li class="flex items-center"><span class="w-2 h-2 bg-orange-500 rounded-full mr-2"></span>Roads - Connect territories</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-blue-500 rounded-full mr-2"></span>Settlements - 1 victory point each</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-purple-500 rounded-full mr-2"></span>Cities - 2 victory points each</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-green-500 rounded-full mr-2"></span>Development Cards - Special abilities</li>
                    </ul>
                </div>

                <!-- Victory Conditions -->
                <div class="bg-gray-50 dark:bg-gray-900 rounded-lg p-6">
                    <h5 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Path to Victory</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400 mb-4">Multiple ways to earn victory points:</p>
                    <ul class="text-sm text-gray-600 dark:text-gray-400 space-y-2">
                        <li class="flex items-center"><span class="w-2 h-2 bg-green-500 rounded-full mr-2"></span>Settlements (1 point each)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-green-500 rounded-full mr-2"></span>Cities (2 points each)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-green-500 rounded-full mr-2"></span>Longest Road (2 points)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-green-500 rounded-full mr-2"></span>Largest Army (2 points)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-green-500 rounded-full mr-2"></span>Development Cards (1 point each)</li>
                    </ul>
                </div>

                <!-- Trading System -->
                <div class="bg-gray-50 dark:bg-gray-900 rounded-lg p-6">
                    <h5 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Strategic Trading</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400 mb-4">Engage in commerce to get needed resources:</p>
                    <ul class="text-sm text-gray-600 dark:text-gray-400 space-y-2">
                        <li class="flex items-center"><span class="w-2 h-2 bg-blue-500 rounded-full mr-2"></span>Player-to-player trading</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-blue-500 rounded-full mr-2"></span>Maritime trade (ports)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-blue-500 rounded-full mr-2"></span>Bank trading (4:1 ratio)</li>
                        <li class="flex items-center"><span class="w-2 h-2 bg-blue-500 rounded-full mr-2"></span>Specialized ports (2:1 ratio)</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Key Features -->
        <div class="mt-8">
            <h4 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">Game Features</h4>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <div class="bg-white dark:bg-gray-900 p-6 rounded-lg border border-gray-200 dark:border-gray-700">
                    <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Competitive Multiplayer</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400">3-4 player strategic competition with interactive trading and building.</p>
                </div>
                <div class="bg-white dark:bg-gray-900 p-6 rounded-lg border border-gray-200 dark:border-gray-700">
                    <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Random Board Setup</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400">Each game features a unique island layout ensuring fresh gameplay experiences.</p>
                </div>
                <div class="bg-white dark:bg-gray-900 p-6 rounded-lg border border-gray-200 dark:border-gray-700">
                    <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Rules Enforcement</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400">The game ensures all player actions follow official Settlers of Catan rules.</p>
                </div>
                <div class="bg-white dark:bg-gray-900 p-6 rounded-lg border border-gray-200 dark:border-gray-700">
                    <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Save & Load</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400">Save games in progress and continue your conquest at any time.</p>
                </div>
                <div class="bg-white dark:bg-gray-900 p-6 rounded-lg border border-gray-200 dark:border-gray-700">
                    <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Modern JavaFX UI</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400">Clean, intuitive interface with BootstrapFX styling for enhanced gameplay.</p>
                </div>
                <div class="bg-white dark:bg-gray-900 p-6 rounded-lg border border-gray-200 dark:border-gray-700">
                    <h5 class="font-semibold text-gray-900 dark:text-white mb-2">Strategic Depth</h5>
                    <p class="text-sm text-gray-600 dark:text-gray-400">Multiple paths to victory with complex resource management and diplomacy.</p>
                </div>
            </div>
        </div>

        <!-- Technical Implementation -->
        <div class="mt-8">
            <h4 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">Technical Implementation</h4>
            <div class="bg-gray-50 dark:bg-gray-900 rounded-lg p-6">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <h5 class="font-semibold text-gray-900 dark:text-white mb-3">Architecture</h5>
                        <ul class="text-sm text-gray-600 dark:text-gray-400 space-y-2">
                            <li>• <strong>JavaFX Framework:</strong> Modern UI with FXML layouts</li>
                            <li>• <strong>MVC Pattern:</strong> Separation of game logic and presentation</li>
                            <li>• <strong>Event-driven:</strong> Responsive user interactions and game events</li>
                        </ul>
                    </div>
                    <div>
                        <h5 class="font-semibold text-gray-900 dark:text-white mb-3">Technologies</h5>
                        <ul class="text-sm text-gray-600 dark:text-gray-400 space-y-2">
                            <li>• <strong>Java 16:</strong> Modern language features and performance</li>
                            <li>• <strong>JavaFX:</strong> Rich desktop application framework</li>
                            <li>• <strong>Maven:</strong> Dependency and build management</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- Installation Instructions -->
        <div class="mt-8">
            <h4 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">Getting Started</h4>
            <div class="bg-gray-50 dark:bg-gray-900 rounded-lg p-6">
                <ol class="list-decimal list-inside space-y-3 text-gray-600 dark:text-gray-400">
                    <li><a href="https://www.oracle.com/java/technologies/downloads/" target="_blank" class="text-primary-600 hover:text-primary-700 dark:text-primary-500">Download</a> the latest JRE to run the .jar file</li>
                    <li><a href="https://github.com/nitheesh-cpu/Settlers-Of-Catan/releases/download/Download/Catan.jar" target="_blank" class="text-primary-600 hover:text-primary-700 dark:text-primary-500">Download</a> the project .jar file from GitHub</li>
                    <li>Double-click the file to start your settlement adventure</li>
                    <li>Gather 3-4 players for the ultimate strategic experience</li>
                </ol>
            </div>
        </div>

        <!-- GitHub and Download Buttons -->
        <div class="mt-8 flex flex-col sm:flex-row gap-4">
            <a href="https://github.com/nitheesh-cpu/Settlers-Of-Catan" target="_blank" title="View Source Code"
                class="text-white inline-flex items-center justify-center bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
                role="button">
                <svg aria-hidden="true" class="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 24 24">
                    <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" clip-rule="evenodd"></path>
                </svg>
                View Source Code
            </a>
            <a href="https://github.com/nitheesh-cpu/Settlers-Of-Catan/releases/download/Download/Catan.jar" target="_blank" title="Download Game"
                class="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700 inline-flex items-center justify-center"
                role="button">
                <svg aria-hidden="true" class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
                </svg>
                Download Game
            </a>
            <a href="https://www.catan.com/understand-catan/game-rules" target="_blank" title="Official Rules"
                class="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700 inline-flex items-center justify-center"
                role="button">
                <svg aria-hidden="true" class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
                </svg>
                Official Rules
            </a>
        </div>
    </div>

</div>
