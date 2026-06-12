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

# Players to test
ONLINE_PLAYER_UUID = "de11f3ca-003f-3713-b771-62bc481dbfca" # AdityaOkeGas2
OFFLINE_PLAYER_UUID = "9c045595-a5da-4284-9d9e-c3603f86432b" # Daichi_keii

ENDPOINTS = [
    {
        "name": "Server Status",
        "path": "/server",
        "method": "GET"
    },
    {
        "name": "Server Plugins",
        "path": "/server/plugins",
        "method": "GET"
    },
    {
        "name": "Online Players Summary",
        "path": "/players",
        "method": "GET"
    },
    {
        "name": "All Players (Paged)",
        "path": "/players/all",
        "method": "GET"
    },
    {
        "name": "Online Player Details (UUID)",
        "path": f"/players/{ONLINE_PLAYER_UUID}",
        "method": "GET"
    },
    {
        "name": "Offline Player Details (UUID)",
        "path": f"/players/{OFFLINE_PLAYER_UUID}",
        "method": "GET"
    },
    {
        "name": "Online Player Stats",
        "path": f"/players/{ONLINE_PLAYER_UUID}/stats",
        "method": "GET"
    },
    {
        "name": "Offline Player Stats",
        "path": f"/players/{OFFLINE_PLAYER_UUID}/stats",
        "method": "GET"
    },
    {
        "name": "Offline Player Snapshot",
        "path": f"/players/offline/{OFFLINE_PLAYER_UUID}",
        "method": "GET"
    }
]

def make_request(path, method="GET"):
    url = f"{API_URL}{path}"
    req = urllib.request.Request(url, method=method)
    req.add_header("Authorization", f"Bearer {API_KEY}")
    req.add_header("User-Agent", "NaturalBridge-Tester/1.0")
    
    try:
        with urllib.request.urlopen(req, timeout=10) as response:
            status = response.status
            body = response.read().decode('utf-8')
            try:
                data = json.loads(body)
                return status, data
            except json.JSONDecodeError:
                return status, body
    except urllib.error.HTTPError as e:
        status = e.code
        body = e.read().decode('utf-8')
        try:
            data = json.loads(body)
            return status, data
        except json.JSONDecodeError:
            return status, body
    except Exception as e:
        return 500, {"error": str(e)}

def main():
    print("Starting API Endpoint Testing...")
    print(f"Target API: {API_URL}")
    
    output_lines = []
    output_lines.append("# NaturalAPI Endpoint Test Results")
    output_lines.append(f"Generated on: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    output_lines.append(f"Target API: `{API_URL}`")
    output_lines.append("")
    output_lines.append("## Summary Table")
    output_lines.append("")
    output_lines.append("| Endpoint Name | Method | Path | Status | Result |")
    output_lines.append("|---|---|---|---|---|")
    
    results = []
    for endpoint in ENDPOINTS:
        name = endpoint["name"]
        path = endpoint["path"]
        method = endpoint["method"]
        print(f"Testing {name} ({method} {path})...")
        status, response = make_request(path, method)
        results.append((endpoint, status, response))
        
        status_badge = f"![Status {status}](https://img.shields.io/badge/Status-{status}-green)" if status == 200 else f"![Status {status}](https://img.shields.io/badge/Status-{status}-red)"
        output_lines.append(f"| {name} | `{method}` | `{path}` | {status_badge} | [Go to Details](#{name.lower().replace(' ', '-').replace('(', '').replace(')', '')}) |")

    output_lines.append("")
    output_lines.append("---")
    output_lines.append("")
    
    for endpoint, status, response in results:
        name = endpoint["name"]
        path = endpoint["path"]
        method = endpoint["method"]
        
        output_lines.append(f"### {name}")
        output_lines.append(f"- **Method:** `{method}`")
        output_lines.append(f"- **URL:** `{API_URL}{path}`")
        output_lines.append(f"- **Status Code:** `{status}`")
        output_lines.append("")
        output_lines.append("Response:")
        output_lines.append("```json")
        output_lines.append(json.dumps(response, indent=2))
        output_lines.append("```")
        output_lines.append("")
        output_lines.append("[Back to Summary](#summary-table)")
        output_lines.append("")
        output_lines.append("---")
        output_lines.append("")
        
    output_file = "api_test_results.md"
    try:
        with open(output_file, "w", encoding="utf-8") as f:
            f.write("\n".join(output_lines))
        print(f"Successfully generated test results in: {output_file}")
    except Exception as e:
        print(f"Error writing output file: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    main()
