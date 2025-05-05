# Novacraft
# Welcome to Project Novacraft!
Novacraft is an ambitious sandbox game developed by Novaforge Studios, designed to rival Minecraft while addressing its shortcomings. Built from scratch using Java 23 and LWJGL 3.3.6, Novacraft introduces innovative features like unlimited world height, no world borders, a Greenstone automation system, self-sustaining villagers, and a fully integrated open-source modding API. Our mission is to create a player-friendly experience with no microtransactions, frequent updates (every 2 months), and cross-platform support (PC, mobile, web, and future consoles).
Project Vision
Novacraft aims to become the "second king of sandbox games" by offering:

Unlimited Creativity: No hardcoded limits on build height or world size.
Unique Mechanics: Greenstone (a Redstone counterpart), Fusion Anvil, thirst bar, enhanced hearts, and a reworked XP system.
Rich Dimensions: Sky Dimension, Inferno (replacing the Nether), and boss fights like the Red Dragon.
Community-Driven Modding: An open-source modding API built into the core game, unlike Minecraft’s external modloaders (Forge, Fabric).
Player-First Philosophy: One-time purchase ($16.99), no pay-to-win, and compliance with EU regulations against predatory monetization.

File Structure
The project is organized under the base package com.novaforgestudios.novacraft with a non-monolithic structure:

src/com/novaforgestudios/novacraft/: Java source code, split into core, world, entity, mechanics, network, modding, util, and resources packages.
assets/: Original and Creative Commons textures, models, and sounds.
config/: Game, control, and server settings.
lib/: Manually managed dependencies (LWJGL 3.3.6, Netty).
docs/: Modding API documentation, architecture overview, and changelog.
tests/: Unit and integration tests.

Setup Instructions

Clone the Repository:
Use a private Git repository (e.g., GitHub private repo) to protect the project.
Command: git clone <repository-url>.


Add Dependencies:
Download LWJGL 3.3.6 jars (lwjgl.jar, lwjgl-opengl.jar, lwjgl-openal.jar, lwjgl-glfw.jar) and platform-specific natives from lwjgl.org.
Download netty-all-4.1.111.jar from netty.io.
Place jars in Novacraft/lib/.


Configure IDE:
Eclipse:
Import the project: File > Import > Existing Projects into Workspace.
Add jars to build path: Right-click project > Build Path > Add External Archives > Select lib/ jars.


VSCode:
Open the Novacraft folder.
Edit settings.json: Add "java.project.referencedLibraries": ["lib/*.jar"].




Run the Game:
Set com.novaforgestudios.novacraft.core.Game as the main class.
Ensure assets in assets/ are accessible (relative paths in util/AssetLoader.java).


Collaborate Securely:
Use Eclipse’s Codetogether plugin for trusted team members only.
Share session codes via secure channels (e.g., encrypted email).
Commit changes frequently to Git and maintain backups.



The Codetogether Incident
In a past incident, two individuals, Gurt and Snip3rs, pressured the project lead to delete the Novacraft project, falsely claiming it violated Mojang/Microsoft’s copyrights and trademarks related to Minecraft. They gained access via the Codetogether plugin in Eclipse and successfully coerced the deletion of the project. This was a significant setback, but it underscored critical lessons:

Lesson Learned: Never share Codetogether codes with untrusted individuals. Limit access to verified team members and use role-based permissions (e.g., read-only for guests).
Preventive Measures:
Store the project in a private Git repository with regular commits.
Backup src/, assets/, and config/ daily to a cloud service (e.g., Google Drive) or external drive.
Log changes in docs/changelog.md and use Git’s blame feature to track contributions.


Clarification on Copyright/Trademark:
Novacraft is an original game with unique assets (created by Novaforge Studios or sourced from Creative Commons, e.g., OpenGameArt.org).
We use distinct names (e.g., Novacraft, Greenstone, Inferno) and mechanics to avoid infringing on Mojang’s IP.
The open-source modding API and non-monolithic codebase further differentiate Novacraft from Minecraft.



Mojang/Microsoft Brand Guidelines
To address concerns raised during the incident, we reference Mojang’s Minecraft Brand Guidelines to ensure compliance:

Personal Use:
Mojang allows relaxed use for personal creations, stating, “Pretty much anything goes there - so go for it and have fun, just remember the policies and don’t do anything illegal or infringing on others.”
Novacraft’s development falls under personal use during its pre-release phase, as it’s not yet shared publicly.


Commercial Use:
Mojang defines commercial use as “any uses of our name, brand, or assets that you use and share with others (regardless of whether you receive payment or provide it for free).”
Novacraft avoids using Minecraft’s name, brand, or assets. All content (e.g., textures, models, sounds) is original or Creative Commons-licensed.
Upon release, Novacraft will adhere to commercial guidelines by maintaining its distinct identity and not leveraging Minecraft’s IP.


Our Commitment:
We respect Mojang’s IP and have consulted legal resources to ensure Novacraft is a standalone project.
Any similarities (e.g., sandbox gameplay, block-based mechanics) are genre conventions, not direct copies.



Legal and Ethical Stance

No Infringement: Novacraft uses no Minecraft assets, code, or trademarked terms (e.g., “Minecraft 2”). Our branding (Novacraft, Novaforge Studios) is unique.
Asset Sourcing: Textures, models, and sounds are created by Novaforge Studios or sourced from Creative Commons platforms (e.g., OpenGameArt.org) with verified licenses.
Modding API: The open-source modding API (com.novaforgestudios.novacraft.modding) will be publicly available under a permissive license (e.g., MIT) to foster community contributions without exposing proprietary code.
Player-Friendly: Novacraft commits to a one-time purchase model ($16.99), no microtransactions, and no pay-to-win mechanics, aligning with EU regulations and community values.

Contact Us
For inquiries, collaboration requests, or legal concerns, please contact Novaforge Studios at:

Email: novaforgestudios078@gmail.com
Note: We welcome feedback and community input but reserve the right to verify all collaborators to prevent incidents like the one with Gurt and Snip3rs.

Contributing

Developers: Join our team by emailing us with your skills and experience. Access is granted only after verification.
Modders: Stay tuned for the modding API documentation (docs/modding_api.md) upon alpha release.
Community: Follow our progress on [insert future platforms, e.g., Discord, X] for updates and beta testing opportunities.

Roadmap

Year 1: Core game loop, world generation, and modding API (alpha release).
Year 2: Multiplayer, mobile/web support, and Sky Dimension/Inferno (beta release).
Year 3: Full release ($16.99) with 2-month update cycles.
No Deadline: Quality is our priority, ensuring a polished experience.

Acknowledgments

LWJGL Team: For providing a lightweight, cross-platform game library.
OpenGameArt.org: For Creative Commons assets that inspire our designs.
Community: For inspiring us to create a sandbox game that respects players.

Thank you for supporting Novacraft. Let’s build a new sandbox legacy together!
