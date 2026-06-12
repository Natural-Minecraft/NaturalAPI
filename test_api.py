#!/usr/bin/env python3
import json
import urllib.request
import urllib.error
import sys
import os
from datetime import datetime

# API Configuration
API_URL = "https://napi.aikeigroup.net/api/v1"
API_KEY = "2f27591d-d0ba-4ef9-a796-cf8a9f35c175.f22dab8d36f942df8802239e7b040190"

# Target resources
ONLINE_PLAYER_UUID = "de11f3ca-003f-3713-b771-62bc481dbfca" # AdityaOkeGas2
ONLINE_PLAYER_NAME = "AdityaOkeGas2"
OFFLINE_PLAYER_UUID = "9c045595-a5da-4284-9d9e-c3603f86432b" # Daichi_keii
OFFLINE_PLAYER_NAME = "Daichi_keii"
WORLD_NAME = "world"
GROUP_NAME = "default"

CATEGORIES = {
    "Server": [
        {"name": "Server General Status", "path": "/server", "method": "GET"},
        {"name": "Server Detail Status", "path": "/server/status", "method": "GET"},
        {"name": "Server TPS", "path": "/server/tps", "method": "GET"},
        {"name": "Server MSPT", "path": "/server/mspt", "method": "GET"},
        {"name": "Server RAM", "path": "/server/ram", "method": "GET"},
        {"name": "Server Uptime", "path": "/server/uptime", "method": "GET"},
        {"name": "Server Version", "path": "/server/version", "method": "GET"},
        {"name": "Server Players Count", "path": "/server/players/count", "method": "GET"},
        {"name": "Server Plugins", "path": "/server/plugins", "method": "GET"},
        {"name": "Server Whitelist", "path": "/server/whitelist", "method": "GET"},
        {"name": "Server Banlist", "path": "/server/banlist", "method": "GET"},
        {"name": "Server Playtime Leaderboard", "path": "/server/leaderboard?type=playtime&limit=5", "method": "GET"}
    ],
    "Online Player Details": [
        {"name": "Online Players Summary", "path": "/players", "method": "GET"},
        {"name": "All Players (Paged)", "path": "/players/all", "method": "GET"},
        {"name": "Player Details (UUID)", "path": f"/players/{ONLINE_PLAYER_UUID}", "method": "GET"},
        {"name": "Player Details (Name)", "path": f"/players/name/{ONLINE_PLAYER_NAME}", "method": "GET"},
        {"name": "Player Location", "path": f"/players/{ONLINE_PLAYER_UUID}/location", "method": "GET"},
        {"name": "Player Health", "path": f"/players/{ONLINE_PLAYER_UUID}/health", "method": "GET"},
        {"name": "Player Experience", "path": f"/players/{ONLINE_PLAYER_UUID}/experience", "method": "GET"},
        {"name": "Player Gamemode", "path": f"/players/{ONLINE_PLAYER_UUID}/gamemode", "method": "GET"},
        {"name": "Player Inventory", "path": f"/players/{ONLINE_PLAYER_UUID}/inventory", "method": "GET"},
        {"name": "Player Inventory Hotbar", "path": f"/players/{ONLINE_PLAYER_UUID}/inventory/hotbar", "method": "GET"},
        {"name": "Player Inventory Armor", "path": f"/players/{ONLINE_PLAYER_UUID}/inventory/armor", "method": "GET"},
        {"name": "Player Inventory Offhand", "path": f"/players/{ONLINE_PLAYER_UUID}/inventory/offhand", "method": "GET"},
        {"name": "Player Active Effects", "path": f"/players/{ONLINE_PLAYER_UUID}/effects", "method": "GET"},
        {"name": "Player Skin", "path": f"/players/{ONLINE_PLAYER_UUID}/skin", "method": "GET"},
        {"name": "Player Ping", "path": f"/players/{ONLINE_PLAYER_UUID}/ping", "method": "GET"},
        {"name": "Player Network Data", "path": f"/players/{ONLINE_PLAYER_UUID}/network", "method": "GET"},
        {"name": "Player Stats", "path": f"/players/{ONLINE_PLAYER_UUID}/stats", "method": "GET"},
        {"name": "Player Permissions", "path": f"/players/{ONLINE_PLAYER_UUID}/permissions", "method": "GET"},
        {"name": "Player Permission Check (essentials.fly)", "path": f"/players/{ONLINE_PLAYER_UUID}/permission/essentials.fly", "method": "GET"},
        {"name": "Player Snapshot details", "path": f"/players/{ONLINE_PLAYER_UUID}/snapshot", "method": "GET"},
        {"name": "Trigger Player Snapshot", "path": f"/players/{ONLINE_PLAYER_UUID}/snapshot", "method": "POST"}
    ],
    "Offline Player Details": [
        {"name": "Offline Player Details (UUID)", "path": f"/players/offline/{OFFLINE_PLAYER_UUID}", "method": "GET"},
        {"name": "Offline Player Details (Name)", "path": f"/players/offline/name/{OFFLINE_PLAYER_NAME}", "method": "GET"},
        {"name": "Offline Player Stats", "path": f"/players/{OFFLINE_PLAYER_UUID}/stats", "method": "GET"}
    ],
    "Worlds": [
        {"name": "Worlds List", "path": "/worlds", "method": "GET"},
        {"name": "World Detail", "path": f"/worlds/{WORLD_NAME}", "method": "GET"},
        {"name": "World Time", "path": f"/worlds/{WORLD_NAME}/time", "method": "GET"},
        {"name": "World Weather", "path": f"/worlds/{WORLD_NAME}/weather", "method": "GET"},
        {"name": "World Players", "path": f"/worlds/{WORLD_NAME}/players", "method": "GET"},
        {"name": "World Entities", "path": f"/worlds/{WORLD_NAME}/entities", "method": "GET"},
        {"name": "World Loaded Chunks", "path": f"/worlds/{WORLD_NAME}/chunks", "method": "GET"},
        {"name": "World Border", "path": f"/worlds/{WORLD_NAME}/border", "method": "GET"},
        {"name": "World Gamerules", "path": f"/worlds/{WORLD_NAME}/gamerules", "method": "GET"}
    ],
    "Integrations": [
        {"name": "Vault Player Data", "path": f"/vault/player/{ONLINE_PLAYER_UUID}", "method": "GET"},
        {"name": "Vault Groups", "path": "/vault/groups", "method": "GET"},
        {"name": "Vault Group Details", "path": f"/vault/groups/{GROUP_NAME}", "method": "GET"},
        {"name": "Vault Economy Status", "path": "/vault/economy/status", "method": "GET"},
        {"name": "LuckPerms Player Data", "path": f"/luckperms/player/{ONLINE_PLAYER_UUID}", "method": "GET"},
        {"name": "LuckPerms Groups", "path": "/luckperms/groups", "method": "GET"},
        {"name": "LuckPerms Group Details", "path": f"/luckperms/groups/{GROUP_NAME}", "method": "GET"},
        {"name": "LuckPerms Group Members", "path": f"/luckperms/groups/{GROUP_NAME}/members", "method": "GET"},
        {"name": "LuckPerms Group Permissions", "path": f"/luckperms/groups/{GROUP_NAME}/permissions", "method": "GET"},
        {"name": "PAPI Plugins", "path": "/papi/plugins", "method": "GET"},
        {"name": "PAPI Evaluate Placeholders", "path": "/papi/evaluate", "method": "POST", "body": {"placeholders": ["%server_name%", "%essentials_nickname%"]}},
        {"name": "NaturalSchool Player Data", "path": f"/naturalschool/player/{ONLINE_PLAYER_UUID}", "method": "GET"},
        {"name": "NaturalSchool Refresh", "path": "/naturalschool/refresh", "method": "POST"}
    ],
    "Admin": [
        {"name": "Admin Health", "path": "/admin/health", "method": "GET"},
        {"name": "Admin OpenAPI (JSON)", "path": "/admin/openapi.json", "method": "GET", "preview_limit": 1000},
        {"name": "Admin API Keys", "path": "/admin/keys", "method": "GET"},
        {"name": "Admin Config", "path": "/admin/config", "method": "GET"},
        {"name": "Admin Rate Limits", "path": "/admin/rate-limits", "method": "GET"},
        {"name": "Admin Snapshot History", "path": "/admin/snapshot/history", "method": "GET"}
    ]
}

def make_request(path, method="GET", body=None):
    url = f"{API_URL}{path}"
    req = urllib.request.Request(url, method=method)
    req.add_header("Authorization", f"Bearer {API_KEY}")
    req.add_header("User-Agent", "NaturalBridge-Tester/1.0")
    
    if body is not None:
        req.add_header("Content-Type", "application/json")
        req.data = json.dumps(body).encode('utf-8')
        
    try:
        with urllib.request.urlopen(req, timeout=10) as response:
            status = response.status
            body_bytes = response.read()
            body_str = body_bytes.decode('utf-8')
            try:
                data = json.loads(body_str)
                return status, data
            except json.JSONDecodeError:
                return status, body_str
    except urllib.error.HTTPError as e:
        status = e.code
        body_str = e.read().decode('utf-8')
        try:
            data = json.loads(body_str)
            return status, data
        except json.JSONDecodeError:
            return status, body_str
    except Exception as e:
        return 500, {"error": str(e)}

def main():
    print("Starting Comprehensive API Endpoint Testing...")
    print(f"Target API: {API_URL}")
    
    output_lines = []
    output_lines.append("# NaturalAPI Full Endpoint Test Report")
    output_lines.append(f"Generated on: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    output_lines.append(f"Target API base URL: `{API_URL}`")
    output_lines.append("")
    output_lines.append("This document contains test results for **every registered endpoint** of the NaturalAPI plugin.")
    output_lines.append("")
    output_lines.append("## Table of Contents")
    for category in CATEGORIES.keys():
        anchor = category.lower().replace(" ", "-")
        output_lines.append(f"- [{category}](#{anchor})")
    output_lines.append("")
    
    # Run tests and collect data
    category_results = {}
    for category, endpoints in CATEGORIES.items():
        print(f"\n--- Testing Category: {category} ---")
        category_results[category] = []
        for ep in endpoints:
            name = ep["name"]
            path = ep["path"]
            method = ep["method"]
            body = ep.get("body")
            
            print(f"[{method}] {path} ({name})...")
            status, res = make_request(path, method, body)
            
            # Post-processing to truncate very large JSONs (e.g. openapi)
            preview_limit = ep.get("preview_limit")
            if preview_limit and isinstance(res, str) and len(res) > preview_limit:
                res = res[:preview_limit] + "\n... (Truncated for readability) ..."
            elif preview_limit and isinstance(res, dict) and len(str(res)) > preview_limit:
                res = {"info": "Response content truncated for readability.", "data_preview": list(res.items())[:5]}
                
            category_results[category].append({
                "name": name,
                "path": path,
                "method": method,
                "status": status,
                "response": res
            })

    # Render category summaries and detailed blocks
    for category, results in category_results.items():
        output_lines.append(f"## {category}")
        output_lines.append("")
        output_lines.append("| Endpoint Name | Method | Path | Status | Details |")
        output_lines.append("|---|---|---|---|---|")
        
        for r in results:
            status = r["status"]
            status_badge = f"![Status {status}](https://img.shields.io/badge/Status-{status}-green)" if status in [200, 201] else f"![Status {status}](https://img.shields.io/badge/Status-{status}-red)"
            anchor = r["name"].lower().replace(" ", "-").replace("(", "").replace(")", "").replace("/", "")
            output_lines.append(f"| {r['name']} | `{r['method']}` | `{r['path']}` | {status_badge} | [View Details](#{anchor}) |")
        output_lines.append("")
        
        for r in results:
            anchor = r["name"].lower().replace(" ", "-").replace("(", "").replace(")", "").replace("/", "")
            output_lines.append(f"### {r['name']}")
            output_lines.append(f"- **Method:** `{r['method']}`")
            output_lines.append(f"- **Path:** `{r['path']}`")
            output_lines.append(f"- **HTTP Status:** `{r['status']}`")
            output_lines.append("")
            output_lines.append("<details>")
            output_lines.append("<summary>🔍 Click to expand/collapse full JSON Response</summary>")
            output_lines.append("")
            output_lines.append("```json")
            output_lines.append(json.dumps(r["response"], indent=2))
            output_lines.append("```")
            output_lines.append("</details>")
            output_lines.append("")
            output_lines.append(f"[Back to {category} Summary](#{category.lower().replace(' ', '-')})")
            output_lines.append("")
            output_lines.append("---")
            output_lines.append("")

    output_file = "api_test_results.md"
    try:
        with open(output_file, "w", encoding="utf-8") as f:
            f.write("\n".join(output_lines))
        print(f"\nAll tests completed. Output written to: {output_file}")
    except Exception as e:
        print(f"Error writing output file: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    main()
